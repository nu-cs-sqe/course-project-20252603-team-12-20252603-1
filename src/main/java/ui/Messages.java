package ui;

import java.util.ResourceBundle;

public final class Messages {

  private static final ResourceBundle BUNDLE = ResourceBundle.getBundle("messages");

  private Messages() {}

  public static String get(String key) {
    return BUNDLE.getString(key);
  }
}
