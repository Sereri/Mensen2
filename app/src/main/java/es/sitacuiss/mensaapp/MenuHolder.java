package es.sitacuiss.mensaapp;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * ViewHolder for Menus
 */
public class MenuHolder extends RecyclerView.ViewHolder {
    final TextView name;
    final TextView description;
    final TextView price;
    final ImageView iconBeef;
    final ImageView iconPork;
    final ImageView iconVenison;
    final ImageView iconLamb;
    final ImageView iconPoultry;
    final ImageView iconFish;
    final ImageView iconSeafood;
    final ImageView iconVegetarian;
    final ImageView iconVegan;
    final ImageView iconHomemade;
    final ImageView iconAllergen;
    final View item;

    public MenuHolder(View itemView) {
        super(itemView);
        item = itemView.findViewById(R.id.menu_menu);
        name = (TextView) itemView.findViewById(R.id.menu_name);
        description = (TextView) itemView.findViewById(R.id.menu_description);
        price = (TextView) itemView.findViewById(R.id.menu_price);
        iconBeef = (ImageView) itemView.findViewById(R.id.menu_icon_beef);
        iconPork = (ImageView) itemView.findViewById(R.id.menu_icon_pork);
        iconVenison = (ImageView) itemView.findViewById(R.id.menu_icon_venison);
        iconLamb = (ImageView) itemView.findViewById(R.id.menu_icon_lamb);
        iconPoultry = (ImageView) itemView.findViewById(R.id.menu_icon_poultry);
        iconFish = (ImageView) itemView.findViewById(R.id.menu_icon_fish);
        iconSeafood = (ImageView) itemView.findViewById(R.id.menu_icon_seafood);
        iconVegetarian = (ImageView) itemView.findViewById(R.id.menu_icon_vegetarian);
        iconVegan = (ImageView) itemView.findViewById(R.id.menu_icon_vegan);
        iconHomemade = (ImageView) itemView.findViewById(R.id.menu_icon_homemade);
        iconAllergen = (ImageView) itemView.findViewById(R.id.menu_icon_allergen);
    }
}
