$(function(){
    $('[data-toggle="tooltip"]').tooltip();
    util = {
        fillFormField:function(form,data){
            let formEl = $(form);
            $.each(data,function(index,item){
                let inputEl =  formEl.find("[name="+index+"]");//.val(item);
                let type = inputEl.attr("type");
                if(type == "radio"){
                    let radio_length = inputEl.length;
                    for(let i = 0;i< radio_length;i++){
                        if($(inputEl[i]).val() == item){
                            $(inputEl[i]).attr("checked",true);
                        }else{
                            $(inputEl[i]).removeAttr("checked");
                        }
                    }
                }else{
                    inputEl.val(item);
                }
            });
        }
    }
    String.prototype.endWith=function(str){
        if(str==null||str==""||this.length==0||str.length>this.length)
            return false;
        if(this.substring(this.length-str.length)==str)
            return true;
        else
            return false;
        return true;
    }
});
