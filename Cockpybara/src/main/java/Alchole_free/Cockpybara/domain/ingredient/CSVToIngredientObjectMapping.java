package Alchole_free.Cockpybara.domain.ingredient;

import com.opencsv.CSVReader;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CSVToIngredientObjectMapping {
    public static void main(String[] args) {
        String csvFile = "C:\\CockpyPj\\Cockpybara\\Cockpybara\\src\\main\\resources\\ingredients_list.csv"; // 읽을 CSV 파일 경로

        try (CSVReader reader = new CSVReader(new FileReader(csvFile))) {
            CsvToBean<Ingredient> csvToBean = new CsvToBeanBuilder<Ingredient>(reader)
                    .withType(Ingredient.class) // 매핑할 클래스 지정
                    .withIgnoreLeadingWhiteSpace(true) // 앞뒤 공백 무시
                    .build();

            List<Ingredient> ingredients = csvToBean.parse(); // CSV 데이터를 객체로 매핑

            for (Ingredient ingredient : ingredients) {
                System.out.println(ingredient.getId() + ", " + ingredient.getName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
