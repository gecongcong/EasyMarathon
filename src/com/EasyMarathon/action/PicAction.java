package com.EasyMarathon.action;

import java.applet.AppletContext;
import java.io.IOException;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.EasyMarathon.bean.PicBean;
import com.EasyMarathon.bean.Token;
import com.EasyMarathon.bean.WeixinOauth2Token;
import com.EasyMarathon.service.PicService;
import com.opensymphony.xwork2.ActionContext;
import com.EasyMarathon.util.AdvancedUtil;
import com.EasyMarathon.util.CommonUtil;

public class PicAction
{
	private PicBean picture;
	private String eventID;
	private String wechatID;

	public String getWechatID() {
		return wechatID;
	}

	public void setWechatID(String wechatID) {
		this.wechatID = wechatID;
	}

	public PicBean getPicture()
	{
		return picture;
	}

	public void setPicture(PicBean picture)
	{
		this.picture = picture;
	}

	public String getEventID()
	{
		return eventID;
	}

	public void setEventID(String eventID)
	{
		this.eventID = eventID;
	}

	public String uploadPic()
	{
		// System.out.println(picture.getFile().getAbsolutePath());
		PicService picservice = new PicService();

		if (picservice.uploadPicService(picture.getFile(), eventID))
			return "success";
		else
			return "fail";
	}

	public String uploadPicforUser(){
		// System.out.println(picture.getFile().getAbsolutePath());
		PicService picservice = new PicService();
		System.out.println("�����û���Ƭ�ϴ�������");
		System.out.println("΢�źţ�"+wechatID);
		System.out.println("���ºţ�"+eventID);
		System.out.println(picture.getPicStatus());
		
		HttpServletRequest request = ServletActionContext.getRequest();
		try {
			ServletInputStream in = request.getInputStream();
			int index=-1;
			byte[] b=new byte[1024];
			System.out.println("��ӡrequest");
			while((index = in.read(b))!=-1){
				System.out.println(b.toString());
			}
			System.out.println();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println();
		if (picservice.uploadPicforUserService(picture.getFile(), eventID,wechatID))
			return "success";
		else
			return "fail";
	}

	public String uploadforUser(){//��ͨ�û��ϴ���Ƭ
		System.out.println("--------< uploadforUser >--------");
		System.out.println();
		try {
			// �������û�Ψһƾ֤
			String appId = "wxa6bb25947675b744";
			// �������û�Ψһƾ֤��Կ
			String appSecret = "c39ae4fc9da658a6642e2dd47626a45f";
			String code = (String)ServletActionContext.getRequest().getSession().getAttribute("code");
			// ��ȡ��ҳ��Ȩaccess_token
			WeixinOauth2Token weixinOauth2Token = AdvancedUtil
								.getOauth2AccessToken("wxa6bb25947675b744",
								"c39ae4fc9da658a6642e2dd47626a45f", code);
			// ��ҳ��Ȩ�ӿڷ���ƾ֤
			String accessToken = weixinOauth2Token.getAccessToken();
			
			System.out.println("--------< accessToken >--------");
			System.out.println(accessToken);
			String type="image";
			String mediaFilUrl=picture.getFile().getAbsolutePath();
			System.out.println("--------< mediaFilUrl >--------");
			System.out.println(mediaFilUrl);
			AdvancedUtil.uploadMedia(accessToken,type,mediaFilUrl);
		}catch(Exception e) {
			e.printStackTrace();
			return "fail";
		}
		return "success";
	}
	
}
