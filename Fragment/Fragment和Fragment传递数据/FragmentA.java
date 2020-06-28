
FragmentA fromFragment = new FragmentA();
FragmentB toFragment = new FragmentB();

//注意下面这一句，必须要指定目标Fragment
toFragment.setTargetFragment(fromFragment,100);

//隐藏FragmentA显示FragmentB
FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().setTransition(android.R.animator.fade_in);
transaction.hide(fromFragment)
           .add(R.id.switch_frameLayout, toFragment)
           .addToBackStack(null)
           .commit();

@Override
 public void onActivityResult(int requestCode, int resultCode, Intent data) {
     super.onActivityResult(requestCode, resultCode, data);
     if (resultCode == Activity.RESULT_OK) {
         //开始接收返回的数据
         String photoPath = data.getStringExtra("photoPath");
     }
 }

//可能会遇到的问题:如果Activity拦截了result,需要在Activity中转发
@Override
protected void onActivityResult(int requestCode, int resultCode, Intent data) {
   super.onActivityResult(requestCode, resultCode, data);
   //转发返回的结果给fragment
   fromFragment.onActivityResult(requestCode,resultCode,data);
}