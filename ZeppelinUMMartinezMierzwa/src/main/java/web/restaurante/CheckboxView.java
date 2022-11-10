package web.restaurante;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import javax.inject.Named;

import org.primefaces.event.UnselectEvent;

import persistencia.jpa.bean.CategoriaRestaurante;
import persistencia.jpa.dao.CategoriaRestauranteDAO;

@Named
@RequestScoped
public class CheckboxView {

	private String[] selectedOptions;
    private List<String> categorias;
    private List<SelectItem> countries;
    private String[] selectedCountries;

    @PostConstruct
    public void init() {
        categorias = new ArrayList<>();

        
        
        SelectItem items[] = new SelectItem[CategoriaRestauranteDAO.getCategoriaRestauranteDAO().getAll().size()];
        
        int i=0;
        for(CategoriaRestaurante c :CategoriaRestauranteDAO.getCategoriaRestauranteDAO().getAll()) {
        	
        	items[i]=new SelectItem(c.getCategoria(),c.getCategoria());
        	categorias.add(c.getCategoria());
        	i++;
        }

        countries = new ArrayList<>();
        SelectItemGroup europeCountries = new SelectItemGroup("European Countries");
        europeCountries.setSelectItems(items);


        countries.add(europeCountries);
    }

    public String[] getSelectedOptions() {
        return selectedOptions;
    }

    public void setSelectedOptions(String[] selectedOptions) {
        this.selectedOptions = selectedOptions;
    }



    public List<String> getcategorias() {
        return categorias;
    }

    public void setcategorias(List<String> categorias) {
        this.categorias = categorias;
    }

    public List<SelectItem> getCountries() {
        return countries;
    }

    public void setCountries(List<SelectItem> countries) {
        this.countries = countries;
    }

    public String[] getSelectedCountries() {
        return selectedCountries;
    }

    public void setSelectedCountries(String[] selectedCountries) {
        this.selectedCountries = selectedCountries;
    }

    public void onItemUnselect(UnselectEvent event) {
        FacesMessage msg = new FacesMessage();
        msg.setSummary("Item unselected: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);

        FacesContext.getCurrentInstance().addMessage(null, msg);
    }

    public void selectedOptionsChanged() {
        String message = "selectedOptions changed to: ";
        if (selectedOptions != null) {
            for (int i = 0; i < selectedOptions.length; i++) {
                if (i > 0) {
                    message += ", ";
                }
                message += selectedOptions[i];
            }
        }

        FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO, message, null));
    }


}

