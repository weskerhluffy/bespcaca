var logger = log4javascript.getLogger();
logger.setLevel(log4javascript.Level.TRACE);
var popUpAppender = new log4javascript.PopUpAppender();
popUpAppender.setFocusPopUp(true);
popUpAppender.setNewestMessageAtTop(false);
logger.addAppender(popUpAppender);