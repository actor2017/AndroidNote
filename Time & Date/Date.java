
        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        Date date = new Date();

        boolean before = date.before(Date when);
        boolean after = date.after(Date when);
        int compare = date.compareTo(Date anotherDate);
        date.setTime(long time);
        boolean equals = date.equals(Object obj);//比较time
        long time1 = date.getTime();
        int i = date.hashCode();//根据time算出
        Instant instant = date.toInstant();
        String s = date.toString();

        //过时方法
        int year = date.getYear();
        int month = date.getMonth();
        int day = date.getDay();
        int hours = date.getHours();
        int minutes = date.getMinutes();
        int seconds = date.getSeconds();
        int timezoneOffset = date.getTimezoneOffset();

        date.setYear(int year);
        date.setMonth(int month);
        date.setDate(int date1);
        date.setHours(int hours);
        date.setMinutes(int minutes);
        date.setSeconds(int seconds);

        String s1 = date.toGMTString();
        String s2 = date.toLocaleString();
