package com.zhuangjie.gather;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.io.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.Map;

public class _4_LinkedHashSet {
    public static void main(String[] args) throws IOException, InterruptedException {

        
        JsonGet jsonGet = new JsonGet(new JsonGetResources().load("/jt.json").read());

        JsonGet up = jsonGet.down("data").down("text").up("^"); //向下获取data,再向下获取test后向上获取^对象，^初始化时就被定义为最顶层
        System.out.println(up.down("has_more_items").get()); 
       

    }
}
