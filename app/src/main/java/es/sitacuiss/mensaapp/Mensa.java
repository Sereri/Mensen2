package es.sitacuiss.mensaapp;

/**
 * Mensa Enum
 */
public enum Mensa {
    HOCHSCHULE_MANNHEIM,
    MENSA_AM_SCHLOSS,
    CAFETERIA_KUBUS,
    CAFETERIA_MUSIKHOCHSCHULE,
    MENSARIA_WOHLGELEGEN,
    SCHLOSS_EHRENHOF,
    MENSARIA_METROPOL;


    public static int getValue(Mensa enumMensa){
        switch (enumMensa){
            case HOCHSCHULE_MANNHEIM:
                return 1;
            case MENSA_AM_SCHLOSS:
                return 2;
            case CAFETERIA_KUBUS:
                return 3;
            case CAFETERIA_MUSIKHOCHSCHULE:
                return 4;
            case MENSARIA_WOHLGELEGEN:
                return 5;
            case SCHLOSS_EHRENHOF:
                return 6;
            case MENSARIA_METROPOL:
                return 7;
            default: return 0;
        }
    }

    public static Mensa getMensa(int intMensa){
        switch (intMensa){
            case 1:
                return HOCHSCHULE_MANNHEIM;
            case 2:
                return MENSA_AM_SCHLOSS;
            case 3:
                return CAFETERIA_KUBUS;
            case 4:
                return CAFETERIA_MUSIKHOCHSCHULE;
            case 5:
                return MENSARIA_WOHLGELEGEN;
            case 6:
                return SCHLOSS_EHRENHOF;
            case 7:
                return MENSARIA_METROPOL;
            default: return HOCHSCHULE_MANNHEIM;
        }
    }
}
