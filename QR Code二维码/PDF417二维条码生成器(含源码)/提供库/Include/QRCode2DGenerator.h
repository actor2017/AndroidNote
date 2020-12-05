#ifndef QRCode2DGenerator_h__
#define QRCode2DGenerator_h__
#include "QRCode2DGeneratorExport.h"
#include <QtGui/QImage>

namespace QRCode2DGeneratorNS
{
	QImage QRCODE2DGENERATOR_EXPORT MakePdf417Bitmap(const char* pInText);
};


#endif // QRCode2DGenerator_h__
