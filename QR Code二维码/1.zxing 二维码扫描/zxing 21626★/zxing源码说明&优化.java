android-core 模块


core 模块


android Eclipse工程
├── camera			//相机的配置和管理，相机自动聚焦功能，以及相机成像回调（通过byte[]数组返回实际的数据）
│   ├── AutoFocusCallback.java
│   ├── CameraConfigurationManager.java
│   ├── CameraManager.java
│   └── PreviewCallback.java
├── //decode 原来的decode包, 现在就在包名下
│	//图片解析相关类, 通过相机扫描二维码和解析图片使用两套逻辑。
│	//前者对实时性要求比较高，后者对解析结果要求较高，因此采用不同的配置。
│	//相机扫描: 主要在DecodeHandler里通过串行的方式解析，图片识别: 主要通过线程DecodeImageThread异步调用返回回调的结果。
│   ├── CaptureActivityHandler.java
│   ├── DecodeHandler.java
│   ├── DecodeImageCallback.java//没有
│   ├── DecodeImageThread.java//没有
│   ├── DecodeManager.java//没有
│   ├── DecodeThread.java
│   ├── FinishListener.java	//和 ↓ 用来控制长时间无活动时自动销毁创建的Activity
│   ├── InactivityTimer.java//和 ↑ 用来控制长时间无活动时自动销毁创建的Activity
│   └── ViewfinderView		//类绘制扫描区域框，并且必须在扫描区域里才能识别二维码













