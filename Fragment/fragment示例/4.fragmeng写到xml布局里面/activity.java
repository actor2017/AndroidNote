//这种玩法常用的是大屏手机上，左边一个MenuFragment，右边一个ContentFragment，
//左边控制右边的内容。当然也有一个Fragment占满全屏的用法，比如高德地图MapView提供的MapFragment。
FragmetnManager fManager = getSupportFragmentManager();
SignInFragment signInFragment = (SignInFragment)fManager.findFragmentById(R.id.fragment_sign_in);