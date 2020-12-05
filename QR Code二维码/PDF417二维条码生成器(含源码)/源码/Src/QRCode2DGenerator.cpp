#include "../Include/QRCode2DGenerator.h"
#include "pdf417lib.h"
#include <string>
#include <QtGui/QColor>

QImage QRCODE2DGENERATOR_EXPORT QRCode2DGeneratorNS::MakePdf417Bitmap( const char* pInText )
{
	if (NULL == pInText)
	{
		return QImage();
	}

	pdf417param p;
	pdf417init(&p); //initial of the PDF417 struct
	p.text = (char*)pInText;
	p.options = PDF417_INVERT_BITMAP;

	paintCode(&p);  //get the row bit map
	if (p.error) 
	{
		pdf417free(&p);
		return QImage();
	}
	
	int x, y;
	const int moudle = 5;

	char bitOffset = 0, tem;
	int bmpW, bmpH;
	bmpW = p.bitColumns / 8 + 1;
	bmpH = p.codeRows;
	int m, n; //¶þÎ¬ÂëÏÔÊ¾Î»ÖÃ
	x = 0;
	y = 0;

	char* pOutBitmap = p.outBits;

	QImage img(p.bitColumns * moudle + p.codeRows,
		p.codeRows * moudle, QImage::Format_RGB32);
	img.fill(Qt::white);

	for (int i = 0; i < bmpW*bmpH; ++i)
	{
		tem = *pOutBitmap++;
		for (int b = 0; b < 8; b++)
		{
			if (!(tem & 0x80))
			{
				for (m = 0; m < moudle; m++)
				{
					img.setPixel(x + m, y, 0);//black dot
					for (n = 0; n < moudle; n++)
					{
						img.setPixel(x + m, y + n, 0);//black dot
					}
				}
			}
			else
			{
				for (m = 0; m < moudle; m++)
				{
					img.setPixel(x + m, y, 0xffffffff);//white dot
					for (n = 0; n < moudle; n++)
					{
						img.setPixel(x + m, y + n, 0xffffffff);//white dot
					}
				}
			}
			tem <<= 1;
			x += moudle;
		}
		if ((i+1) % bmpW == 0)
		{
			y+= moudle;
			x = 0;
		}
	}

	pdf417free(&p);
	return img;
}
 