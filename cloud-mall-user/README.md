# 						用户模块需求文档

1. **授权登录：**用户首次点开小程序，调取微信登录API，保存回调openid等个人信息，再次登录只是更新用户信息。
2. **绑定手机：**微信授权登录成功，“我的”界面显示微信头像和微信名称，首次点击后触发注册页面，验证验证码绑定手机号，跳转“我的”页面。再次点击头像名称，跳转个人详细信息页面。