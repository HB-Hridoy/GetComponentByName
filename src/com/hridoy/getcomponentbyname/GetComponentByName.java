package com.hridoy.getcomponentbyname;

import android.util.Log;
import com.google.appinventor.components.annotations.SimpleFunction;
import com.google.appinventor.components.runtime.AndroidNonvisibleComponent;
import com.google.appinventor.components.runtime.Component;
import com.google.appinventor.components.runtime.ComponentContainer;
import com.google.appinventor.components.runtime.ReplForm;
import kawa.standard.Scheme;

import java.lang.reflect.Field;

public class GetComponentByName extends AndroidNonvisibleComponent {
  public String TAG = "GetComponentByName";

  public GetComponentByName(ComponentContainer container) {
    super(container.$form());
  }

  private Object lookupComponentInRepl(String componentName) {
    Scheme lang = Scheme.getInstance();
    try {
      // Since we're in the REPL, we can cheat and invoke the Scheme interpreter to get the method.
      Object result = lang.eval("(begin (require <com.google.youngandroid.runtime>)(get-component " +
              componentName + "))");
      if (result instanceof Component) {
        return (Component) result;
      } else {
        Log.e(TAG, "Wanted a Component, but got a " +
                (result == null ? "null" : result.getClass().toString()));
      }
    } catch (Throwable throwable) {
      throwable.printStackTrace();
    }
    return "";
  }


  private Object lookupComponentInForm(String componentName) {
    try {
      // Get the field by name
      Field field = form.getClass().getField(componentName);
      // Get the field's value, since field itself isn't a Component
      Object component = field.get(form);
      if (component instanceof Component) {
        return (Component) component;
      } else {
        Log.e(TAG, "Wanted a Component, but got a " +
                (component == null ? "null" : component.getClass().toString()));
      }
    } catch (NoSuchFieldException | IllegalAccessException e) {
      Log.e(TAG, "Error accessing component: " + componentName, e);
    }
    //
    return "";
  }


  @SimpleFunction(description = "")
  public Object GetComponentByName(String componentName) {
    if (form instanceof ReplForm) {
      return lookupComponentInRepl(componentName);
    } else {
      return lookupComponentInForm(componentName);
    }
  }
}
