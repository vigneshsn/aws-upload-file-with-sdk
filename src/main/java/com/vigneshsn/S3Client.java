package com.vigneshsn;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Region;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.util.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;


/**
 * Hello world!
 *
 */
public class S3Client
{


    public static void main( String[] args )
    {
        //configuration
        String bucketName = "*****";
        String fileName = "new.txt";
        String accessKey = "******";
        String secret = "*****";

        AWSCredentials credentials = new BasicAWSCredentials(accessKey, secret);

        URL url = S3Client.class.getClassLoader().getResource("new.txt");
        AmazonS3Client client = new AmazonS3Client(credentials);
        Region region = Region.getRegion(Regions.EU_WEST_1);
        client.setRegion(region);
        try(InputStream is = url.openStream()){
            byte[] fileContent = IOUtils.toByteArray(is);
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentLength(fileContent.length);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(fileContent);
            PutObjectRequest request = new PutObjectRequest(bucketName, fileName, byteArrayInputStream, metadata);
            client.putObject(request);
        } catch (Exception ex) {
            ex.printStackTrace();
        }


//        GetObjectRequest getObjectRequest = new GetObjectRequest(bucketName, fileName);
//
//        try(InputStream is = client.getObject(getObjectRequest).getObjectContent()) {
//            //BufferedInputStream reader = new BufferedInputStream(is);
//            String fileContent = IOUtils.toString(is);
//            System.out.println(fileContent);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        System.out.println("File successfully created in aws "+ fileName);

    }
}
