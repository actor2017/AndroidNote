//��ȡ���ذ�ť��ǰ������  
                float x = iv_back.getX();  
                float y = iv_back.getY();  
                //��ȡ��x,y������λ�õ�rgb��ɫֵ  
                int pixel = bitmap_gs.getPixel((int)x,(int)y);  
                int redValue = Color.red(pixel);  
                int blueValue = Color.blue(pixel);  
                int greenValue = Color.green(pixel);  
                //��������ȡ����ɫȡ�����������ɫ  
                int color = Color.rgb(Math.abs(redValue - 255),Math.abs(greenValue - 255),Math.abs(blueValue-255));  
                //�Է��ذ�ť�������ɫ���ˣ���֤��ǰ���ذ�ť����ɫ���䱳��ɫ�෴  
                iv_back.setColorFilter(color);  