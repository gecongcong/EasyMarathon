package com.EasyMarathon.servlet;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.apache.struts2.ServletActionContext;

import com.EasyMarathon.bean.WeixinMedia;
import com.EasyMarathon.bean.WeixinOauth2Token;
import com.EasyMarathon.util.AdvancedUtil;
import com.EasyMarathon.util.CommonUtil;

import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.DiskFileUpload;
import org.apache.commons.fileupload.FileItem;  
import org.apache.commons.fileupload.FileUploadException;  
import org.apache.commons.fileupload.disk.DiskFileItemFactory;  
import org.apache.commons.fileupload.servlet.ServletFileUpload; 

public class UserUpload extends HttpServlet{

	private List<String> filenames=new ArrayList<String>();
	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException 
	{
		System.out.println("--------< uploadforUser >--------");
		System.out.println();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		try {
			/*// �������û�Ψһƾ֤
			String appId = "wxa6bb25947675b744";
			// �������û�Ψһƾ֤��Կ
			String appSecret = "c39ae4fc9da658a6642e2dd47626a45f";
			String accessToken = CommonUtil.getToken(appId, appSecret).getAccessToken();
			System.out.println("--------< accessToken >--------");
			System.out.println(accessToken);
			String type="image";*/
			getPictureUrl(request, response);
			/*for(int i=0;i<filenames.size();i++){
				System.out.println("--------< mediaFilUrl >--------");
				String pictureUrl = filenames.get(i);
				System.out.println("pictureUrl: "+pictureUrl);
				WeixinMedia media = AdvancedUtil.uploadMedia(accessToken,type,pictureUrl);
				String mediaId = media.getMediaId();
				String savePath = "http://120.27.106.188/easyrun/uploadPicture";
				AdvancedUtil.getMedia(accessToken, mediaId, savePath);
			}*/
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
	public void getPictureUrl(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		//��ô����ļ���Ŀ����  
        DiskFileItemFactory factory = new DiskFileItemFactory(); 
        
        //���� ����Ĵ�С�����ϴ��ļ������������û���ʱ��ֱ�ӷŵ� ��ʱ�洢��  
        //String eventId = request.getParameter("eventID");
        //System.out.println("eventId: "+eventId);
        //int eventID = Integer.parseInt(eventId);
        String path = "C:\\Users\\91337\\Desktop\\UserPicture\\";
        factory.setRepository(new File(path));  
        factory.setSizeThreshold(1024*1024); 
        //��ˮƽ��API�ļ��ϴ�����  
        ServletFileUpload upload = new ServletFileUpload(factory); 
        
        try {  
            //�����ϴ�����ļ�  
        	List<FileItem> list = (List<FileItem>)upload.parseRequest(request);
        	
        	for(FileItem item : list){  
            	//��ȡ������������  
            	String name = item.getFieldName();  
            	//�����ȡ�� ����Ϣ����ͨ�� �ı� ��Ϣ  
                if(item.isFormField()){                     
                    //��ȡ�û�����������ַ��� ����Ϊ���ύ�������� �ַ������͵�  
                    String value = item.getString();
                    System.out.println("item.name:"+name);
                    System.out.println("item.value:"+value);
                    path += value+"\\";
                    break;
                }  
            }
        	
            for(FileItem item : list){  
            	//��ȡ������������  
            	String name = item.getFieldName();  
            	System.out.println("item.name:"+name);

                if(!item.isFormField()){ //��ͼƬ          
                	//��ȡ·����  
                    String value = item.getName();
                    System.out.println("item.value:"+value);
                    int start = value.lastIndexOf("\\");
                    //��ȡ �ϴ��ļ��� �ַ������֣���1�� ȥ����б��
                    String filename = value.substring(start+1); 
                    System.out.println("path: "+path);
                    File fileParent = new File(path);
                    if  (!fileParent .exists()  && !fileParent.isDirectory())      
                    {       
                        //System.out.println("//������");  
                        fileParent.mkdirs();
                    }
                    File fileChild = new File(path,filename);
                    OutputStream out = new FileOutputStream(fileChild);  
                    InputStream in = item.getInputStream();  
                    int length = 0 ;  
                    byte [] buf = new byte[1024] ;  
                      
                    System.out.println("��ȡ�ϴ��ļ����ܹ���������"+item.getSize());  
  
                    // in.read(buf) ÿ�ζ��������ݴ����   buf ������  
                    while( (length = in.read(buf) ) != -1){  
                        //��   buf ������ ȡ������ д�� ���������������  
                        out.write(buf, 0, length);  
                    }
                    in.close();  
                    out.close();
                }  
            }
        }catch (FileUploadException e) {  
        	e.printStackTrace();  
        	request.getRequestDispatcher("bg/upLoadunsuccess.jsp").forward(request, response);
	    }  
	    catch (Exception e) {           
	        e.printStackTrace();  
	        request.getRequestDispatcher("bg/upLoadunsuccess.jsp").forward(request, response);
	    }
        request.getRequestDispatcher("bg/uploadsuccess.jsp").forward(request, response);
	}
}
