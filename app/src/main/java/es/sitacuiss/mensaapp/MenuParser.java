package es.sitacuiss.mensaapp;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

/**
 * Utility class for parsing
 */
public class MenuParser {
    private static final String TAG = "MenuParser";

    private static MenuParser parser;

    public static MenuParser getInstance() {
        if (parser == null) {
            parser = new MenuParser();
        }
        return parser;
    }

    /**
     * Parses the webpage containing the menu
     * @param webpage String of webpage containing menu
     * @return ArrayList of Menus
     */
    public ArrayList<Menu> parseMenu(String webpage) {
        Document webSoup = Jsoup.parse(webpage);

        ArrayList<Menu> menuList = new ArrayList<>();

        Element plan = webSoup.getElementById("mensa_plan");

        Elements tables = plan.getElementsByTag("table");

        for (Element table : tables) {
            Elements menus = table.children().first().getElementsByTag("tr");
            for (Element menu : menus) {
                Menu currentMenu = new Menu();
                currentMenu.setName(menu.children().first().text());
                if (menu.child(1).getElementsByTag("b").size() > 0 && menu.child(1).getElementsByTag("b").first().hasText()) {
                    currentMenu.setName(menu.child(1).children().first().text());
                }
                Elements additives = menu.child(1).getElementsByTag("p").first().getElementsByTag("sup").clone();
                currentMenu.setAdditives(additives.text());
                menu.child(1).getElementsByTag("p").first().getElementsByTag("sup").remove();
                currentMenu.setDescription(menu.child(1).getElementsByTag("p").first().text());
                currentMenu.setPrice(menu.children().last().text());
                ArrayList<MenuIcons> iconList = new ArrayList<>();
                Elements icons = menu.getElementsByClass("icons");
                if (icons.first().children().size() > 0) {
                    for (Element icon : icons.first().children()) {
                        if (icon.hasClass("icon-carrot")) {
                            iconList.add(MenuIcons.VEGETARIAN);
                        } else if (icon.hasClass("icon-leaf")) {
                            iconList.add(MenuIcons.VEGAN);
                        } else if (icon.hasClass("icon-pig")) {
                            iconList.add(MenuIcons.PORK);
                        } else if (icon.hasClass("icon-cow")) {
                            iconList.add(MenuIcons.BEEF);
                        } else if (icon.hasClass("icon-deer")) {
                            iconList.add(MenuIcons.VENISON);
                        } else if (icon.hasClass("icon-sheep")) {
                            iconList.add(MenuIcons.LAMB);
                        } else if (icon.hasClass("icon-chicken")) {
                            iconList.add(MenuIcons.POULTRY);
                        } else if (icon.hasClass("icon-fish")) {
                            iconList.add(MenuIcons.FISH);
                        } else if (icon.hasClass("icon-shellfish")) {
                            iconList.add(MenuIcons.SEAFOOD);
                        } else if (icon.hasClass("icon-cooking_pot")) {
                            iconList.add(MenuIcons.HOMEMADE);
                        }
                    }
                }
                currentMenu.setIcons(iconList);
                menuList.add(currentMenu);
            }
        }

        return menuList;
    }

    /**
     * Parses the webpage containing the select field with the days for display
     * @param response
     * @return
     */
    public ArrayList<String> parseSelectField(String response) {
        ArrayList<String> days = new ArrayList<>();
        Document webSoup = Jsoup.parse(response);
        Element plan = webSoup.getElementById("mensa_plan");
        Element daySelect = plan.getElementsByClass("day-select").first().children().first();
        Elements dayOptions = daySelect.getElementsByTag("option");
        for (Element dayOption : dayOptions) {
            days.add(dayOption.text().replace("\u00a0", "").replace("(heute)", ""));
        }
        return days;
    }
}
