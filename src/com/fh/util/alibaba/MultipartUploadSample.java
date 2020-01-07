package com.fh.util.alibaba;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.web.multipart.MultipartFile;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CompleteMultipartUploadRequest;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.InitiateMultipartUploadRequest;
import com.aliyun.oss.model.InitiateMultipartUploadResult;
import com.aliyun.oss.model.ListPartsRequest;
import com.aliyun.oss.model.PartETag;
import com.aliyun.oss.model.PartListing;
import com.aliyun.oss.model.PartSummary;
import com.aliyun.oss.model.UploadPartRequest;
import com.aliyun.oss.model.UploadPartResult;
import com.fh.util.DateHelper;

/**
 * This sample demonstrates how to upload multiparts to Aliyun OSS 
 * using the OSS SDK for Java.
 */
public class MultipartUploadSample {
	private static String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
    private static String accessKeyId = "LTAIBuCMwxx9ETDE";
    private static String accessKeySecret = "JUPts8LOFUgQ0bHVeU6penKGop02T2";
    private static String bucketName = "sgyzxjy";
    private static String filePath = "http://sgyzxjy.oss-cn-hangzhou.aliyuncs.com";
    private static String key = "";
	
    private static OSSClient client = null;
    
    private static String localFilePath = "C:/Users/Administrator/Desktop/12121111212.mp4";
    
    private static ExecutorService executorService = Executors.newFixedThreadPool(5);
    private static List<PartETag> partETags = Collections.synchronizedList(new ArrayList<PartETag>());
    
    public static void main(String[] args) throws IOException {
    	
    	//uploadVideo("");
    }
    
    
    public static String uploadVideo(MultipartFile filefile){
    	Date date = new Date();
    	SimpleDateFormat dateFormat=new SimpleDateFormat("/yyyy/MM/dd/");
		String dateStr = dateFormat.format(new Date());
    	String uploadPath = "upload"+dateStr;
		
		String pic_name=DateHelper.getImageName();
		key = uploadPath+pic_name;
		String filePathName  = filePath+"/"+key;
		// 创建OSSClient实例。
		client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
		
        try {
        	
        	//final File sampleFile = new File(localFilePath);
        	final File sampleFile = createSampleFile();
    		String name = sampleFile.getName();
    		String fileType = name.substring(name.lastIndexOf('.'), name.length());
    		key += fileType;
        	
        	/* 步骤1：初始化一个分片上传事件。
    		*/
    		InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
    		InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
    		// 返回uploadId，它是分片上传事件的唯一标识，您可以根据这个ID来发起相关的操作，如取消分片上传、查询分片上传等。
    		String uploadId = result.getUploadId();
    		System.out.println("Claiming a new upload id " + uploadId + "\n");
    		/* 步骤2：上传分片。
    		*/
    		// partETags是PartETag的集合。PartETag由分片的ETag和分片号组成。
    		List<PartETag> partETags =  new ArrayList<PartETag>();
    		// 计算文件有多少个分片。
    		final long partSize = 5 * 1024 * 1024L;   // 5MB
    		long fileLength = sampleFile.length();
    		int partCount = (int) (fileLength / partSize);
    		if (fileLength % partSize != 0) {
    		    partCount++;
    		 }
    		System.out.println("Total parts count " + partCount + "\n");
    		// 遍历分片上传。
    		for (int i = 0; i < partCount; i++) {
    		    long startPos = i * partSize;
    		    long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;
    		    InputStream instream = new FileInputStream(sampleFile);
    		    // 跳过已经上传的分片。
    		    instream.skip(startPos);
    		    UploadPartRequest uploadPartRequest = new UploadPartRequest();
    		    uploadPartRequest.setBucketName(bucketName);
    		    uploadPartRequest.setKey(key);
    		    uploadPartRequest.setUploadId(uploadId);
    		    uploadPartRequest.setInputStream(instream);
    		    // 设置分片大小。除了最后一个分片没有大小限制，其他的分片最小为100KB。
    		    uploadPartRequest.setPartSize(curPartSize);
    		    // 设置分片号。每一个上传的分片都有一个分片号，取值范围是1~10000，如果超出这个范围，OSS将返回InvalidArgument的错误码。
    		    uploadPartRequest.setPartNumber( i + 1);
    			// 每个分片不需要按顺序上传，甚至可以在不同客户端上传，OSS会按照分片号排序组成完整的文件。
    		    UploadPartResult uploadPartResult = client.uploadPart(uploadPartRequest);
    			// 每次上传分片之后，OSS的返回结果会包含一个PartETag。PartETag将被保存到partETags中。
    		    partETags.add(uploadPartResult.getPartETag());
    		    System.out.println("i : " + i );
    		}
    		
    		/* 步骤3：完成分片上传。
    		*/
    		// 排序。partETags必须按分片号升序排列。
    		Collections.sort(partETags, new Comparator<PartETag>() {
    		    public int compare(PartETag p1, PartETag p2) {
    		        return p1.getPartNumber() - p2.getPartNumber();
    		    }
    		});
    		
    		// 在执行该操作时，需要提供所有有效的partETags。OSS收到提交的partETags后，会逐一验证每个分片的有效性。当所有的数据分片验证通过后，OSS将把这些分片组合成一个完整的文件。
    		CompleteMultipartUploadRequest completeMultipartUploadRequest =
    		        new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
    		client.completeMultipartUpload(completeMultipartUploadRequest);
            System.out.println("Fetching an object");
            
        } catch (OSSException oe) {
            System.out.println("Caught an OSSException, which means your request made it to OSS, "
                    + "but was rejected with an error response for some reason.");
            System.out.println("Error Message: " + oe.getErrorCode());
            System.out.println("Error Code:       " + oe.getErrorCode());
            System.out.println("Request ID:      " + oe.getRequestId());
            System.out.println("Host ID:           " + oe.getHostId());
        } catch (ClientException ce) {
            System.out.println("Caught an ClientException, which means the client encountered "
                    + "a serious internal problem while trying to communicate with OSS, "
                    + "such as not being able to access the network.");
            System.out.println("Error Message: " + ce.getMessage());
        } catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            /*
             * Do not forget to shut down the client finally to release all allocated resources.
             */
            if (client != null) {
            	System.out.println("====================================");
                client.shutdown();
            }
        }
        return filePathName;
        
    }
    
    
    
    private static File createSampleFile() throws IOException {
        File file = File.createTempFile("oss-java-sdk-", ".txt");
        file.deleteOnExit();

        Writer writer = new OutputStreamWriter(new FileOutputStream(file));
        for (int i = 0; i < 1000000; i++) {
            writer.write("abcdefghijklmnopqrstuvwxyz\n");
            writer.write("0123456789011234567890\n");
        }
        writer.close();

        return file;
    }
    
    private static String claimUploadId() {
        InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, key);
        InitiateMultipartUploadResult result = client.initiateMultipartUpload(request);
        return result.getUploadId();
    }
    
    private static void completeMultipartUpload(String uploadId) {
        // Make part numbers in ascending order
        Collections.sort(partETags, new Comparator<PartETag>() {

            @Override
            public int compare(PartETag p1, PartETag p2) {
                return p1.getPartNumber() - p2.getPartNumber();
            }
        });
        
        System.out.println("Completing to upload multiparts\n");
        CompleteMultipartUploadRequest completeMultipartUploadRequest = 
                new CompleteMultipartUploadRequest(bucketName, key, uploadId, partETags);
        client.completeMultipartUpload(completeMultipartUploadRequest);
    }
    
    private static void listAllParts(String uploadId) {
        System.out.println("Listing all parts......");
        ListPartsRequest listPartsRequest = new ListPartsRequest(bucketName, key, uploadId);
        PartListing partListing = client.listParts(listPartsRequest);
        
        int partCount = partListing.getParts().size();
        for (int i = 0; i < partCount; i++) {
            PartSummary partSummary = partListing.getParts().get(i);
            System.out.println("\tPart#" + partSummary.getPartNumber() + ", ETag=" + partSummary.getETag());
        }
        System.out.println();
    }
}
