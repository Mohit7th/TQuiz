/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package dataEntry;

public class Question {
    private String qes;
    private String desc;
    private String options[]=new String[4];
    private String ans;
    
    public void setQuestion(String qes){
        this.qes=qes;
    }
    
    public String getQuestion(){
        return qes;
    }
    
    public void setDescription(String desc){
        this.desc=desc;
    }
    
    public String getDescription(){
        return desc;
    }
    
    public void setOptions(String[] options){
        int i=0;
        for(String option: options){
            this.options[i++]=option;
        }
    }
    
    public String[] getOptions(){
        return options;
    }
    
    public void setAns(String ans){
        this.ans=ans;
    }
    
    public String getAns(){
        return ans;
    }
}
