package com.grape.supermarket.tag;

import com.grape.supermarket.common.OperatorSessionHelper;
import com.grape.supermarket.entity.db.PermissionEntity;
import com.grape.supermarket.operator.OperatorSession;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.JspTag;
import javax.servlet.jsp.tagext.SimpleTagSupport;
import java.io.IOException;

/**
 * Created by LXP on 2017/11/14.
 */

public class PermissionTag extends SimpleTagSupport {
    private Integer id;
    private String code;
    private boolean flag;

    @Override
    public void doTag() throws JspException, IOException {
        JspTag parent = getParent();
        if (parent instanceof PermissionTag) {
            if (!((PermissionTag) parent).flag) {
                return;
            }
        }

        if(id != null){
            if(OperatorSessionHelper.hasPermission(id)){
                flag = true;
                getJspBody().invoke(null);
                return ;
            }
        }else if(code != null){
            String[] codes = code.split(",");
            for (String s :codes) {
                if(OperatorSessionHelper.hasPermission(s.trim())){
                    flag = true;
                    getJspBody().invoke(null);
                    return ;
                }
            }
        }
        flag = false;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
