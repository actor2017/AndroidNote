//获取返回按钮当前的坐标  
                float x = iv_back.getX();  
                float y = iv_back.getY();  
                //获取（x,y）所在位置的rgb颜色值  
                int pixel = bitmap_gs.getPixel((int)x,(int)y);  
                int redValue = Color.red(pixel);  
                int blueValue = Color.blue(pixel);  
                int greenValue = Color.green(pixel);  
                //将上述获取的颜色取反，获得新颜色  
                int color = Color.rgb(Math.abs(redValue - 255),Math.abs(greenValue - 255),Math.abs(blueValue-255));  
                //对返回按钮添加新颜色过滤，保证当前返回按钮的颜色与其背景色相反  
                iv_back.setColorFilter(color);  