    //�л���ĳһ��fragment
    private void switchToPosition(int position) {
        FragmentManager supportFragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fl_fram,FragmentFactory.getFragment(position));
        fragmentTransaction.commit();
    }
}