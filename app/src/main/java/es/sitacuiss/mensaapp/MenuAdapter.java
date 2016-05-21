package es.sitacuiss.mensaapp;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * MenuAdapter for the RecyclerView
 */
class MenuAdapter extends RecyclerView.Adapter<MenuHolder> implements View.OnClickListener{
    private static final String TAG = "MenuAdapter";

    Toast mealType;

    public MenuAdapter(MensaFragment fragment) {
        super();
        this.frag = fragment;
        prefs = PreferenceManager.getDefaultSharedPreferences(frag.getContext());
    }

    private final MensaFragment frag;
    private final SharedPreferences prefs;

    @Override
    public MenuHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.menu, parent, false);
        return new MenuHolder(view);
    }

    @Override
    public void onBindViewHolder(MenuHolder holder, int position) {
        if(frag.getMenuList().size()>position) {
            holder.name.setText(frag.getMenuList().get(position).getName());
            holder.description.setText(frag.getMenuList().get(position).getDescription());
            holder.price.setText(frag.getMenuList().get(position).getPrice());
            if(frag.getMenuList().get(position).getIcons() != null) {
                holder.iconBeef.setVisibility(frag.getMenuList().get(position).getIcons().contains(MenuIcons.BEEF) ? View.VISIBLE : View.GONE);
                holder.iconPork.setVisibility(frag.getMenuList().get(position).getIcons().contains(MenuIcons.PORK) ? View.VISIBLE : View.GONE);
                holder.iconVenison.setVisibility(frag.getMenuList().get(position).getIcons().contains(MenuIcons.VENISON) ? View.VISIBLE : View.GONE);
                holder.iconLamb.setVisibility(frag.getMenuList().get(position).getIcons().contains(MenuIcons.LAMB) ? View.VISIBLE : View.GONE);
                holder.iconPoultry.setVisibility(frag.getMenuList().get(position).getIcons().contains(MenuIcons.POULTRY) ? View.VISIBLE : View.GONE);
                holder.iconFish.setVisibility(frag.getMenuList().get(position).getIcons().contains(MenuIcons.FISH) ? View.VISIBLE : View.GONE);
                holder.iconSeafood.setVisibility(frag.getMenuList().get(position).getIcons().contains(MenuIcons.SEAFOOD) ? View.VISIBLE : View.GONE);
                holder.iconVegetarian.setVisibility(frag.getMenuList().get(position).getIcons().contains(MenuIcons.VEGETARIAN) ? View.VISIBLE : View.GONE);
                holder.iconVegan.setVisibility(frag.getMenuList().get(position).getIcons().contains(MenuIcons.VEGAN) ? View.VISIBLE : View.GONE);
                holder.iconHomemade.setVisibility(frag.getMenuList().get(position).getIcons().contains(MenuIcons.HOMEMADE) ? View.VISIBLE : View.GONE);
                holder.iconBeef.setOnClickListener(this);
                holder.iconPork.setOnClickListener(this);
                holder.iconVenison.setOnClickListener(this);
                holder.iconLamb.setOnClickListener(this);
                holder.iconPoultry.setOnClickListener(this);
                holder.iconFish.setOnClickListener(this);
                holder.iconSeafood.setOnClickListener(this);
                holder.iconVegetarian.setOnClickListener(this);
                holder.iconVegan.setOnClickListener(this);
                holder.iconHomemade.setOnClickListener(this);
                checkFilter(holder, position);
                checkAllergens(holder, position);
            }
        }
    }

    @Override
    public int getItemCount() {
        return frag.getMenuList().size();
    }

    /**
     * Check for filtered menu types
     * @param holder MenuHolder
     * @param position position in the menuList
     */
    private void checkFilter(MenuHolder holder, int position) {
        boolean isFiltered = false;

        if (frag.getMenuList().get(position).getIcons().contains(MenuIcons.BEEF) && prefs.getBoolean("filter_beef", false)) {
            isFiltered = true;
        }
        if (frag.getMenuList().get(position).getIcons().contains(MenuIcons.PORK) && prefs.getBoolean("filter_pork", false)) {
            isFiltered = true;
        }
        if (frag.getMenuList().get(position).getIcons().contains(MenuIcons.VENISON) && prefs.getBoolean("filter_venison", false)) {
            isFiltered = true;
        }
        if (frag.getMenuList().get(position).getIcons().contains(MenuIcons.LAMB) && prefs.getBoolean("filter_lamb", false)) {
            isFiltered = true;
        }
        if (frag.getMenuList().get(position).getIcons().contains(MenuIcons.POULTRY) && prefs.getBoolean("filter_poultry", false)) {
            isFiltered = true;
        }
        if (frag.getMenuList().get(position).getIcons().contains(MenuIcons.FISH) && prefs.getBoolean("filter_fish", false)) {
            isFiltered = true;
        }
        if (frag.getMenuList().get(position).getIcons().contains(MenuIcons.SEAFOOD) && prefs.getBoolean("filter_seafood", false)) {
            isFiltered = true;
        }
        if (frag.getMenuList().get(position).getIcons().contains(MenuIcons.VEGETARIAN) && prefs.getBoolean("filter_vegetarian", false)) {
            isFiltered = true;
        }
        if (frag.getMenuList().get(position).getIcons().contains(MenuIcons.VEGAN) && prefs.getBoolean("filter_vegan", false)) {
            isFiltered = true;
        }
        if (frag.getMenuList().get(position).getIcons().contains(MenuIcons.HOMEMADE) && prefs.getBoolean("filter_homemade", false)) {
            isFiltered = true;
        }
        if (prefs.getBoolean("remove_filtered", false)) {
            holder.item.setVisibility(isFiltered ? View.GONE : View.VISIBLE);
        } else {
            holder.item.setAlpha(isFiltered ? 0.2f : 1);
        }
    }

    /**
     * Check for allergens in the menu
     * @param holder MenuHolder
     * @param position position in the menuList
     */
    private void checkAllergens(MenuHolder holder, int position) {
        final ArrayList<String> Allergens = new ArrayList<>();


        if (prefs.getBoolean("allergen_lupinus", false) && frag.getMenuList().get(position).getAdditives().contains("a")) {
            Allergens.add(frag.getString(R.string.lupinus));
        }
        if (prefs.getBoolean("allergen_crayfish", false) && frag.getMenuList().get(position).getAdditives().contains("b")) {
            Allergens.add(frag.getString(R.string.crayfish));
        }
        if (prefs.getBoolean("allergen_sulfite", false) && frag.getMenuList().get(position).getAdditives().contains("c")) {
            Allergens.add(frag.getString(R.string.sulfite));
        }
        if (prefs.getBoolean("allergen_apium", false) && frag.getMenuList().get(position).getAdditives().contains("d")) {
            Allergens.add(frag.getString(R.string.apium));
        }
        if (prefs.getBoolean("allergen_wheat", false) && frag.getMenuList().get(position).getAdditives().contains("e")) {
            Allergens.add(frag.getString(R.string.wheat));
        }
        if (prefs.getBoolean("allergen_gluten", false) && frag.getMenuList().get(position).getAdditives().contains("f")) {
            Allergens.add(frag.getString(R.string.gluten));
        }
        if (prefs.getBoolean("allergen_milk", false) && frag.getMenuList().get(position).getAdditives().contains("g")) {
            Allergens.add(frag.getString(R.string.milk));
        }
        if (prefs.getBoolean("allergen_nuts", false) && frag.getMenuList().get(position).getAdditives().contains("h")) {
            Allergens.add(frag.getString(R.string.nuts));
        }
        if (prefs.getBoolean("allergen_mustard", false) && frag.getMenuList().get(position).getAdditives().contains("i")) {
            Allergens.add(frag.getString(R.string.mustard));
        }
        if (prefs.getBoolean("allergen_soja", false) && frag.getMenuList().get(position).getAdditives().contains("j")) {
            Allergens.add(frag.getString(R.string.soja));
        }
        if (prefs.getBoolean("allergen_sesame", false) && frag.getMenuList().get(position).getAdditives().contains("k")) {
            Allergens.add(frag.getString(R.string.sesame));
        }
        if (prefs.getBoolean("allergen_egg", false) && frag.getMenuList().get(position).getAdditives().contains("l")) {
            Allergens.add(frag.getString(R.string.egg));
        }
        if (prefs.getBoolean("allergen_peanuts", false) && frag.getMenuList().get(position).getAdditives().contains("m")) {
            Allergens.add(frag.getString(R.string.peanuts));
        }
        if (prefs.getBoolean("allergen_fisch", false) && frag.getMenuList().get(position).getAdditives().contains("n")) {
            Allergens.add(frag.getString(R.string.fish));
        }
        if (prefs.getBoolean("allergen_lactose", false) && frag.getMenuList().get(position).getAdditives().contains("o")) {
            Allergens.add(frag.getString(R.string.lactose));
        }
        holder.iconAllergen.setVisibility(Allergens.size() > 0 ? View.VISIBLE : View.GONE);
        holder.iconAllergen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast mealType = Toast.makeText(frag.getContext(), frag.getString(R.string.contains) + TextUtils.join(", ", Allergens), Toast.LENGTH_SHORT);
                mealType.show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        String type;
        switch (v.getId()) {
            case R.id.menu_icon_beef:
                type = frag.getString(R.string.beef);
                break;
            case R.id.menu_icon_pork:
                type = frag.getString(R.string.pork);
                break;
            case R.id.menu_icon_venison:
                type = frag.getString(R.string.venison);
                break;
            case R.id.menu_icon_lamb:
                type = frag.getString(R.string.lamb);
                break;
            case R.id.menu_icon_poultry:
                type = frag.getString(R.string.poultry);
                break;
            case R.id.menu_icon_fish:
                type = frag.getString(R.string.fish);
                break;
            case R.id.menu_icon_seafood:
                type = frag.getString(R.string.seafood);
                break;
            case R.id.menu_icon_vegetarian:
                type = frag.getString(R.string.vegetarian);
                break;
            case R.id.menu_icon_vegan:
                type = frag.getString(R.string.vegan);
                break;
            case R.id.menu_icon_homemade:
                type = frag.getString(R.string.homemade);
                break;
            default:
                return;
        }
        if(mealType != null){
            mealType.cancel();
        }
        mealType = Toast.makeText(frag.getContext(), type, Toast.LENGTH_SHORT);
        mealType.show();
    }
}
