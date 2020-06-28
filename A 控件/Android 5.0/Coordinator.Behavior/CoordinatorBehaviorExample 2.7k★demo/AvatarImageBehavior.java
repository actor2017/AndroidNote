https://github.com/saulmm/CoordinatorBehaviorExample

public static class AvatarImageBehavior extends CoordinatorLayout.Behavior<CircleImageView> {

   @Override
   public boolean layoutDependsOn(CoordinatorLayout parent, CircleImageView, child, View dependency) {
       return dependency instanceof Toolbar;
  }

  public boolean onDependentViewChanged(CoordinatorLayout parent, CircleImageView avatar, View dependency) {
      modifyAvatarDependingDependencyState(avatar, dependency);
   }

  private void modifyAvatarDependingDependencyState(CircleImageView avatar, View dependency) {
        //  avatar.setY(dependency.getY());
        //  avatar.setBlahBlah(dependency.blah / blah);
    }    
}