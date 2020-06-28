package com.mt.mtxx.image;

public class JNI
{
  public native void AutoColor(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public native void AutoColorLevel(byte[] paramArrayOfByte, int paramInt1, int paramInt2, boolean paramBoolean);

  public native void AutoConstrast(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public native void AutoWhiteBalance(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public native void BackWeak(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, int paramInt1, int paramInt2, int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt3, byte[] paramArrayOfByte3, int paramInt4, int paramInt5);

  public native void BackWeak2(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte2, int paramInt5, int paramInt6);

  public native void BackWeakLine(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int[] paramArrayOfInt3, int[] paramArrayOfInt4, float paramFloat, int paramInt3, int paramInt4);

  public native void BackWeakOne(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte, int paramInt5, int paramInt6);

  public native void CColorBalance(boolean paramBoolean, int paramInt1, int paramInt2, int paramInt3, int paramInt4, byte[] paramArrayOfByte, int paramInt5, int paramInt6);

  public native void ColorModeEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  public native void CompositeColorEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void DrawEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  public native void ExclusionEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void ExclusionEx2(byte[] paramArrayOfByte, byte paramByte1, byte paramByte2, byte paramByte3, double paramDouble);

  public native void FaceBeauty(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3);

  public native void FaceBeautyRelease();

  public native void FrameXCBK(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, int paramInt3);

  public native void GaussIIRBlurBrush(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public native void GaussIIRBlurImage(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public native double GetDegreeByPoints(int paramInt1, int paramInt2, int paramInt3, int paramInt4);

  public native void Grayscale(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public native void HardlightEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void ImageColorBurnEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  public native void ImageDarkenEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void ImageExclusionEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void ImageLightenEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void ImageNormalEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void ImageOverlayEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void ImageScreenEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void ImageSoftLightEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void LOMO1(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int[] paramArrayOfInt2);

  public native void LOMO2(int[] paramArrayOfInt1, int paramInt1, int paramInt2, int[] paramArrayOfInt2);

  public native void LightEnEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void MakeAllTransparent(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public native void MultipleEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void NoiseEx(byte[] paramArrayOfByte, long paramLong);

  public native void NormalEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void OldImage(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3, int paramInt4);

  public native void PinLightEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);

  public native void PtChangeByRotate(byte[] paramArrayOfByte, double paramDouble1, double paramDouble2, double paramDouble3);

  public native void RGBtoXYZ(byte paramByte1, byte paramByte2, byte paramByte3, byte[] paramArrayOfByte);

  public native void RGBtoYIQ(byte paramByte1, byte paramByte2, byte paramByte3, byte[] paramArrayOfByte);

  public native void RGBtoYUV(byte paramByte1, byte paramByte2, byte paramByte3, byte[] paramArrayOfByte);

  public native void Resample(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, int paramInt3, byte[] paramArrayOfByte2, int paramInt4, int paramInt5);

  public native void SSkinbeautify(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, double paramDouble);

  public native void ScreenEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void SetBackWeakRadius(byte[] paramArrayOfByte, int paramInt1, int paramInt2);

  public native void SetColorImage(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5);

  public native void SetGrayScaleEx(byte[] paramArrayOfByte);

  public native void Sharp(int[] paramArrayOfInt, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2);

  public native void SharpPreview(int[] paramArrayOfInt, int paramInt1, int paramInt2, float paramFloat1, float paramFloat2);

  public native void SharpPreviewRelease();

  public native void ShiftRGBEx(byte[] paramArrayOfByte, byte paramByte1, byte paramByte2, byte paramByte3);

  public native void SkinWhite(int[] paramArrayOfInt, int paramInt1, int paramInt2, int paramInt3);

  public native void SoftLightEx(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2, double paramDouble);

  public native void StyleBaoColor(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public native void StyleCinnamon(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public native void StyleClassic(int[] paramArrayOfInt, int paramInt1, int paramInt2, double paramDouble);

  public native void StyleClassicStudio(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public native void StyleEP1(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2);

  public native void StyleEP2(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2);

  public native void StyleElegant(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public native void StyleElegantNew(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public native void StyleFP1(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2);

  public native void StyleFP2(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2);

  public native void StyleFilm(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public native void StyleGP1(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  public native void StyleGP2(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  public native void StyleImpression(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public native void StyleJapanese(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public native void StyleLomoAP1(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  public native void StyleLomoAP2(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  public native void StyleLomoB(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public native void StyleLomoBrightRed(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2);

  public native void StyleLomoC(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public native void StyleLomoChristmas(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2);

  public native void StyleLomoHDR(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public native void StyleMilk1(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2);

  public native void StyleMilk2(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2);

  public native void StyleMilk3(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2);

  public native void StyleMilk4(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2);

  public native void StyleOldPhoto(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2);

  public native void StyleOldPhotoP1(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  public native void StyleOldPhotoP2(int[] paramArrayOfInt1, int[] paramArrayOfInt2, int paramInt1, int paramInt2, byte[] paramArrayOfByte, int paramInt3);

  public native void StyleRetro(int[] paramArrayOfInt, int paramInt1, int paramInt2);

  public native void XYZtoRGB(byte paramByte1, byte paramByte2, byte paramByte3, byte[] paramArrayOfByte);

  public native void YIQtoRGB(byte paramByte1, byte paramByte2, byte paramByte3, byte[] paramArrayOfByte);

  public native void YUVtoRGB(byte paramByte1, byte paramByte2, byte paramByte3, byte[] paramArrayOfByte);

  public native int add(int paramInt, byte paramByte);

  public native byte[] deal(byte[] paramArrayOfByte, int paramInt1, int paramInt2, int paramInt3, int paramInt4, String paramString);
}

/* Location:           E:\JNI\day02_JNI\资料\onkey-decompile\mtxx.apk.jar
 * Qualified Name:     com.mt.mtxx.image.JNI
 * JD-Core Version:    0.6.1
 */