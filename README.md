# JsonGet
让后端从json字符串中获取数据更方便


// json数据 {"name":"zhuangjie","age":12}

使用：
JsonGet jsonGet = new JsonGet(json);
System.out.println(jsonGet.down("name").get());

有以下方法
down(String key) //指针向下移
up(String key) //^表示最顶层
get() //获取当前的value
get(String key) //获取指定下面的key对应的value
