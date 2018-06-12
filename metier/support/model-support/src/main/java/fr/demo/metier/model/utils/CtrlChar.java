package fr.demo.metier.model.utils;

/**
 * Caracteres unicodes a supprimer des texte entre CRM et PDS.
 *
 */
public enum CtrlChar {
  

  /**
   * 0000 0 <control>: NULL.
   */
  NULL("\u0000"),
  /**
   * 0001 1 <control>: START OF HEADING.
   */
  START_OF_HEADING("\u0001"),
  /**
   * 0002 2 <control>: START OF TEXT.
   */
  START_OF_TEXT("\u0002"),
  /**
   * 0003 3 <control>: END OF TEXT.
   */
  END_OF_TEXT("\u0003"),
  /**
   * 0004 4 <control>: END OF TRANSMISSION.
   */
  END_OF_TRANSMISSION("\u0004"),
  /**
   * 0005 5 <control>: ENQUIRY.
   */
  ENQUIRY("\u0005"),
  /**
   * 0006 6 <control>: ACKNOWLEDGE.
   */
  ACKNOWLEDGE("\u0006"),
  /**
   * 0007 7 <control>: BELL.
   */
  BELL("\u0007"),
  /**
   * 0008 8 <control>: BACKSPACE.
   */
  BACKSPACE("\u0008"),
  /**
   * 000B 11  <control>: VERTICAL TABULATION.
   */
  VERTICAL_TABULATION("\u000B"),
  /**
   * 000C 12  <control>: FORM FEED.
   */
  FORM_FEED("\u000C"),
  /**
   * 000E 14  <control>: SHIFT OUT.
   */
  SHIFT_OUT("\u000E"),
  /**
   * 000F 15  <control>: SHIFT IN.
   */
  SHIFT_IN("\u000F"),
  /**
   * 0010 16  <control>: DATA LINK ESCAPE.
   */
  DATA_LINK_ESCAPE("\u0010"),
  /**
   * 0011 17  <control>: DEVICE CONTROL ONE.
   */
  DEVICE_CONTROL_ONE("\u0011"),
  /**
   * 0012 18  <control>: DEVICE CONTROL TWO.
   */
  DEVICE_CONTROL_TWO("\u0012"),
  /**
   * 0013 19  <control>: DEVICE CONTROL THREE.
   */
  DEVICE_CONTROL_THREE("\u0013"),
  /**
   * 0014 20  <control>: DEVICE CONTROL FOUR.
   */
  DEVICE_CONTROL_FOUR("\u0014"),
  /**
   * 0015 21  <control>: NEGATIVE ACKNOWLEDGE.
   */
  NEGATIVE_ACKNOWLEDGE("\u0015"),
  /**
   * 0016 22  <control>: SYNCHRONOUS IDLE.
   */
  SYNCHRONOUS_IDLE("\u0016"),
  /**
   * 0017 23  <control>: END OF TRANSMISSION BLOCK.
   */
  END_OF_TRANSMISSION_BLOCK("\u0017"),
  /**
   * 0018 24  <control>: CANCEL.
   */
  CANCEL("\u0018"),
  /**
   * 0019 25  <control>: END OF MEDIUM.
   */
  END_OF_MEDIUM("\u0019"),
  /**
   * 001A 26  <control>: SUBSTITUTE.
   */
  SUBSTITUTE("\u001A"),
  /**
   *  001B 27  <control>: ESCAPE.
   */
  ESCAPE("\u001B"),
  /**
   * 001C 28  <control>: FILE SEPARATOR.
   */
  FILE_SEPARATOR("\u001C"),
  /**
   * 001D 29  <control>: GROUP SEPARATOR.
   */
  GROUP_SEPARATOR("\u001D"),
  /**
   * 001E 30  <control>: RECORD SEPARATOR.
   */
  RECORD_SEPARATOR("\u001E"),
  /**
   * 001F 31  <control>: UNIT SEPARATOR .
   */
  UNIT_SEPARATOR("\u001F"),
  /**
   *  0020 32  SPACE.
   */
  SPACE("\u0020"),
  
  ZERO_WIDTH_SPACE("\u200B");

  private final String value;

  /**
   * Constructeur.
   * 
   * @param value
   *          code de la categorie.
   */
  CtrlChar(String value) {
    this.value = value;
  }

  public String getValue() {
    return this.value;
  }
}
