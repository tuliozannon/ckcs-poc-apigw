package com.ckcspoc.ckcspocapigw.util;

public class DocumentUtil {
    private static Integer MEDICAL_NOTE_TYPE = 100;
    private static Integer DISCHARGED_NOTE_TYPE = 101;

    public static String getDocumentId(Integer soapId, Integer documentType){
        return soapId+"_"+documentType;
    }

    public static Integer getSoapIdFromDocumentId(String documentId){
        return getBaseIds(documentId)[0];
    }

    public static boolean isMedicalNote(String documentId){
        return isMedicalNoteType(getDocumentTypeIdFromDocumentId(documentId));
    }

    public static boolean isDischargedNote(String documentId){
        return isDischargedNoteType(getDocumentTypeIdFromDocumentId(documentId));
    }

    private static boolean isMedicalNoteType(Integer documentType){
        return (documentType != null) && (documentType.equals(MEDICAL_NOTE_TYPE));
    }

    private static boolean isDischargedNoteType(Integer documentType){
        return (documentType != null) && (documentType.equals(DISCHARGED_NOTE_TYPE));
    }

    private static Integer getDocumentTypeIdFromDocumentId(String documentId){
        return getBaseIds(documentId)[1];
    }

    private static Integer[] getBaseIds(String channelId){
        Integer[] baseIds = new Integer[2];
        if (channelId!=null){
            String[] ids = channelId.split("_");
            baseIds[0] = Integer.valueOf(ids[0]);
            baseIds[1] = Integer.valueOf(ids[1]);
        }
        return baseIds;
    }



}
