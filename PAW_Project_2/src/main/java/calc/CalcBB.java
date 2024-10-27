package calc;

import jakarta.inject.Inject;
import jakarta.inject.Named;
import jakarta.enterprise.context.RequestScoped;
import jakarta.faces.application.FacesMessage;
import jakarta.faces.context.FacesContext;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Named("calcBjuBB")
@RequestScoped
public class CalcBB {
    private String weight; // Waga
    private String height; // Wzrost
    private String age;    // Wiek
    private String gender; // Płeć
    private String activityLevel; // Poziom aktywności
    private Double proteinNorm;
    private Double fatNorm;
    private Double carbNorm;
    private Double totalCalories;

    @Inject
    FacesContext ctx;

    // Getters and Setters
    public String getWeight() { return weight; }
    public void setWeight(String weight) { this.weight = weight; }

    public String getHeight() { return height; }
    public void setHeight(String height) { this.height = height; }

    public String getAge() { return age; }
    public void setAge(String age) { this.age = age; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getActivityLevel() { return activityLevel; }
    public void setActivityLevel(String activityLevel) { this.activityLevel = activityLevel; }

    public Double getProteinNorm() { return proteinNorm; }
    public void setProteinNorm(Double proteinNorm) { this.proteinNorm = proteinNorm; }

    public Double getFatNorm() { return fatNorm; }
    public void setFatNorm(Double fatNorm) { this.fatNorm = fatNorm; }

    public Double getCarbNorm() { return carbNorm; }
    public void setCarbNorm(Double carbNorm) { this.carbNorm = carbNorm; }

    public Double getTotalCalories() { return totalCalories; }
    public void setTotalCalories(Double totalCalories) { this.totalCalories = totalCalories; }

    // Obliczanie kalorii na podstawie wzoru Harrisa-Benedicta
    public double calculateCalories() {
        double weight = Double.parseDouble(this.weight);
        double height = Double.parseDouble(this.height);
        double age = Double.parseDouble(this.age);

        double bmr;
        if ("male".equalsIgnoreCase(this.gender)) {
            bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
        } else {
            bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
        }

        // Poziom aktywności
        switch (this.activityLevel.toLowerCase()) {
            case "low":
                return bmr * 1.2; // Niska aktywność
            case "medium":
                return bmr * 1.55; // Średnia aktywność
            case "high":
                return bmr * 1.9; // Wysoka aktywność
            default:
                return bmr * 1.2;
        }
    }

    public boolean calculateMacros() {
        try {
            double calories = calculateCalories();
            this.totalCalories = round(calories, 1);

            // Proporcje BJU
            this.proteinNorm = round((calories * 0.25) / 4, 1); // Białka: 25%
            this.fatNorm = round((calories * 0.30) / 9, 1); // Tłuszcze: 30%
            this.carbNorm = round((calories * 0.45) / 4, 1); // Węglowodany: 45%

            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Operacja zakończona pomyślnie", null));
            return true;
        } catch (Exception e) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Błąd podczas przetwarzania danych", null));
            return false;
        }
    }

    public String calc() {
        if (calculateMacros()) {
            return "showresult";
        }
        return null;
    }

    public String calc_AJAX() {
        if (calculateMacros()) {
            ctx.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, 
                "Kalorie: " + totalCalories + ", Białko: " + proteinNorm + "g, Tłuszcze: " + fatNorm + "g, Węglowodany: " + carbNorm + "g", null));
        }
        return null;
    }

    public String info() {
        return "info";
    }

    // Metoda do zaokrąglania wartości do 1 miejsca po przecinku
    private static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(Double.toString(value));
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
