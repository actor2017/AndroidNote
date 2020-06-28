
    //警用端登录
    private void parseJson(String response) {
        Gson gson = new Gson();
        BaseAPIResult<LoginData> loginResult = gson.fromJson(response, new TypeToken<BaseAPIResult<LoginData>>() {}.getType());
        if (loginResult.errCode == 0) {
            aCache.put(Global.TOKEN_ZHMP, loginResult.data.getToken());
            aCache.put(Global.name,loginResult.data.getName());//姓名
            toast("登陆成功!");
            finish();
            startActivity(new Intent(this, MainActivity.class));
        } else {
            toast(loginResult.errMsg);
        }