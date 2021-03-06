package com.EasyMarathon.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.EasyMarathon.Button.*;
import com.EasyMarathon.bean.Token;
import com.EasyMarathon.util.CommonUtil;
import com.EasyMarathon.util.MenuUtil;

/**
 * 菜单管理器类
 */
public class MenuManager
{
	private static Logger log = LoggerFactory.getLogger(MenuManager.class);

	/**
	 * 定义菜单结构
	 * 
	 * @return
	 */
	private static Menu getMenu()
	{
		ViewButton btn11 = new ViewButton();
		btn11.setName("我要上传");
		btn11.setType("view");
		btn11.setUrl(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa6bb25947675b744&"
						+ "redirect_uri=http://15064r19x0.imwork.net/EasyMarathon/uploadPicforUser.jsp&response_type=code&"
						+ "scope=snsapi_base&state=STATE#wechat_redirect");
		//http://15064r19x0.imwork.net/EasyMarathon/oauthServlet
		
		ViewButton btn12 = new ViewButton();
		btn12.setName("个人中心");
		btn12.setType("view");
		btn12.setUrl(
				"https://open.weixin.qq.com/connect/oauth2/authorize?appid=wxa6bb25947675b744&"
						+ "redirect_uri=http://120.27.106.188/easyrun/RegisterServlet&response_type=code&"
						+ "scope=snsapi_userinfo&state=STATE#wechat_redirect");

		ClickButton btn21 = new ClickButton();
		btn21.setName("照片查询");
		btn21.setType("click");
		btn21.setKey("findPicture");

		ClickButton btn31 = new ClickButton();
		btn31.setName("热门话题");
		btn31.setType("click");
		btn31.setKey("hot");

		ViewButton btn32 = new ViewButton();
		btn32.setName("装备指南");
		btn32.setType("view");
		btn32.setUrl("http://m.taobao.com");

		ClickButton btn33 = new ClickButton();
		btn33.setName("跑城攻略");
		btn33.setType("click");
		btn33.setKey("tip");

		ComplexButton mainBtn1 = new ComplexButton();
		mainBtn1.setName("族友专区");
		mainBtn1.setSub_button(new Button[] { btn11, btn12 });
		/*
		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("照片查询");
		mainBtn2.setSub_button(new Button[] { btn21, btn22, btn23 });
		*/

		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("易跑部落");
		mainBtn3.setSub_button(new Button[] { btn31, btn32, btn33 });

		Menu menu = new Menu();
		menu.setButton(new Button[] { mainBtn1, btn21, mainBtn3 });

		return menu;
	}

	public static void main(String[] args)
	{
		// 第三方用户唯一凭证
		String appId = "wxa6bb25947675b744";
		// 第三方用户唯一凭证密钥
		String appSecret = "c39ae4fc9da658a6642e2dd47626a45f";

		// 调用接口获取凭证
		Token token = CommonUtil.getToken(appId, appSecret);

		if (null != token)
		{
			// 创建菜单
			System.out.println(token);
			//boolean result = MenuUtil.deleteMenu(token.getAccessToken());
			boolean result = MenuUtil.createMenu(getMenu(),token.getAccessToken());
			String ans = MenuUtil.getMenu(token.getAccessToken());
			System.out.println(ans);

			// 判断菜单创建结果
			if (result)
			{
				System.out.println("菜单创建成功！");
				log.info("菜单创建成功！");
			}
			else
			{
				log.info("菜单创建失败！");
				System.out.println("菜单创建失败！");
			}
		}
	}
}
