/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.ghost;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;


public class TrieNode {
    private HashMap<String, TrieNode> children;
    private boolean isWord;

    public TrieNode() {
        children = new HashMap<>();
        isWord = false;
    }

    public void add(String s) {
        TrieNode temp=this;
        for(int i=0;i<s.length();i++)
        {
            if(temp.children.containsKey(""+s.charAt(i)))
                temp=temp.children.get(""+s.charAt(i));
            else {
                TrieNode t = new TrieNode();
                temp.children.put(String.valueOf(s.charAt(i)),t);
                temp=t;
            }

        }
        temp.isWord=true;
    }

    public boolean isWord(String s) {

TrieNode t=this ;
for(int i=0;i<s.length();i++)
{
    t=t.children.get(String.valueOf(s.charAt(i)));
    if(t==null)
        return false;

}
return t.isWord;


     //   return false;
    }

    public String getAnyWordStartingWith(String s) {
        char a[]=s.toCharArray();
        TrieNode t=this;
        for (int i=0;i<a.length;i++)
        {
            if(!t.children.containsKey(""+a[i]))
                return null;
            else {
                t=t.children.get(""+a[i]);
            }
        }

        ArrayList<String> l = new ArrayList<String>(t.children.keySet());
        String r=l.get((int)(Math.random()*(l.size())));
        s=s+r;
        Log.d("getAnyWordStartingWith",s);
        return s;
    }


    public String getGoodWordStartingWith(String s) {
        return null;
    }
}
