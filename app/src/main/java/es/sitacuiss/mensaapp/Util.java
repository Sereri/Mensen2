package es.sitacuiss.mensaapp;

import android.content.Context;

import com.squareup.okhttp.Cache;
import com.squareup.okhttp.Interceptor;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Response;

import java.io.IOException;

/**
 * Utility class for constants and helper functions
 */
class Util {

    private static final String BASE_URL = "https://www.stw-ma.de/";

    private static final String MENSAPLAENE = BASE_URL + "Essen+_+Trinken/Men%C3%BCpl%C3%A4ne/";

    public static final String HOCHSCHULE_MANNHEIM_URL = MENSAPLAENE + "Hochschule+Mannheim";
    public static final String UNI_MANNHEIM_URL = MENSAPLAENE + "Mensa+am+Schloss";
    public static final String CAFETERIA_KUBUS_URL = MENSAPLAENE + "Cafeteria+KUBUS";
    public static final String CAFETERIA_MUSIKHOCHSCHULE_URL = MENSAPLAENE + "Cafeteria+Musikhochschule";
    public static final String MENSARIA_WOHLGELEGEN_URL = MENSAPLAENE + "Mensaria+Wohlgelegen";

    public static final String SCHLOSS_EHRENHOF_URL = BASE_URL + "men%C3%BCplan_eo";
    public static final String MENSARIA_METROPOL_URL = BASE_URL + "speiseplan_mensaria_metropol";

    private static OkHttpClient client;

    public static OkHttpClient getClient(Context context) {
        if (client == null) {
            client = new OkHttpClient();

            int cacheSize = 10 * 1024 * 1024; // 10 MiB
            Cache cache = new Cache(context.getCacheDir(), cacheSize);
            client.setCache(cache);
            client.networkInterceptors().add(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Response originalResponse = chain.proceed(chain.request());
                    return originalResponse.newBuilder()
                            .removeHeader("Pragma")
                            .removeHeader("Expires")
                            .header("Cache-Control", "max-age=0 public")
                            .build();
                }

            });
        }
        return client;
    }

    public static String getTitle(Mensa enumMensa, Context context) {
        switch (enumMensa) {
            case HOCHSCHULE_MANNHEIM:
                return context.getString(R.string.hsma);
            case MENSA_AM_SCHLOSS:
                return context.getString(R.string.mhs);
            case CAFETERIA_KUBUS:
                return context.getString(R.string.kubus);
            case CAFETERIA_MUSIKHOCHSCHULE:
                return context.getString(R.string.mhs);
            case MENSARIA_WOHLGELEGEN:
                return context.getString(R.string.wglg);
            case SCHLOSS_EHRENHOF:
                return context.getString(R.string.eo);
            case MENSARIA_METROPOL:
                return context.getString(R.string.mtpl);
            default:
                return HOCHSCHULE_MANNHEIM_URL;
        }
    }

    public static String getURL(Mensa enumMensa) {
        switch (enumMensa) {
            case HOCHSCHULE_MANNHEIM:
                return HOCHSCHULE_MANNHEIM_URL;
            case MENSA_AM_SCHLOSS:
                return UNI_MANNHEIM_URL;
            case CAFETERIA_KUBUS:
                return CAFETERIA_KUBUS_URL;
            case CAFETERIA_MUSIKHOCHSCHULE:
                return CAFETERIA_MUSIKHOCHSCHULE_URL;
            case MENSARIA_WOHLGELEGEN:
                return MENSARIA_WOHLGELEGEN_URL;
            case SCHLOSS_EHRENHOF:
                return SCHLOSS_EHRENHOF_URL;
            case MENSARIA_METROPOL:
                return MENSARIA_METROPOL_URL;
            default:
                return HOCHSCHULE_MANNHEIM_URL;
        }
    }

    public static int getNavItem(Mensa enumMensa) {
        switch (enumMensa) {
            case HOCHSCHULE_MANNHEIM:
                return R.id.college_hsma;
            case MENSA_AM_SCHLOSS:
                return R.id.college_mas;
            case CAFETERIA_KUBUS:
                return R.id.college_kubus;
            case CAFETERIA_MUSIKHOCHSCHULE:
                return R.id.college_mhs;
            case MENSARIA_WOHLGELEGEN:
                return R.id.college_wglg;
            case SCHLOSS_EHRENHOF:
                return R.id.college_erhf;
            case MENSARIA_METROPOL:
                return R.id.college_mtrp;
            default:
                return R.id.college_hsma;
        }
    }
}
