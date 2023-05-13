package com.example.harjoitustyo;

import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.MapChangeListener;
import javafx.collections.ObservableMap;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * BudjetinLaskuri on JavaFX-sovellus, joka mahdollistaa budjetin ja kulujen laskemisen.
 */

public class BudjetinLaskuri extends Application {

    /**
     * Tietorakenne, joka tallentaa budjettikulut päivämäärän avulla.
     * Avain on Paivays-olio ja arvo on BudjettiKulut-olio.
     */
    private ObservableMap<Paivays, BudjettiKulut> budjettiKulutMap;

    /**
     * BudjettiKulut on sisäluokka, joka edustaa budjetin kulut-tietoja.
     */

    public class BudjettiKulut {
        private double Budjetti;
        private double ruokakulut;
        private double vapaaAjanKulut;
        private double muutKulut;
        private LocalDate pvm;

        /**
         * BudjettiKulut-luokan konstruktori.
         *
         * @param Budjetti         budjetti
         * @param ruokakulut       ruokakulut
         * @param vapaaAjanKulut   vapaa-ajan kulut
         * @param muutKulut        muut kulut
         * @param pvm              päivämäärä
         */
        public BudjettiKulut(double Budjetti, double ruokakulut, double vapaaAjanKulut, double muutKulut, LocalDate pvm) {
            this.Budjetti = Budjetti;
            this.ruokakulut = ruokakulut;
            this.vapaaAjanKulut = vapaaAjanKulut;
            this.muutKulut = muutKulut;
            this.pvm = pvm;
        }
        /**
         * Palauttaa budjetin määrän.
         *
         * @return budjetti
         */
        public double getBudjetti() {
            return Budjetti;
        }
        /**
         * Palauttaa ruokakulut.
         *
         * @return ruokakulut
         */
        public double getRuokakulut() {
            return ruokakulut;
        }
        /**
         * Palauttaa vapaa-ajan kulut.
         *
         * @return vapaa-ajan kulut
         */
        public double getVapaaAjanKulut() {
            return vapaaAjanKulut;
        }
        /**
         * Palauttaa muut kulut.
         *
         * @return muut kulut
         */
        public double getMuutKulut() {
            return muutKulut;
        }
        /**
         * Palauttaa päivämäärän muodossa "pp.kk.vvvv".
         *
         * @return päivämäärä muodossa "pp.kk.vvvv"
         */
        public String getPvmFormatted() {
            return pvm.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        }
    }
        /**
         * Paivays on sisäluokka, joka edustaa päivämäärää.
         */
        public static class Paivays {
            private LocalDate date;

            /**
             * Paivays-luokan konstruktori.
             *
             * @param date päivämäärä
             */
            public Paivays(LocalDate date) {
                this.date = date;
            }

            /**
             * Palauttaa päivämäärän hash-koodin.
             *
             * @return hash-koodi
             */
            @Override
            public int hashCode() {
                return date.hashCode();
            }

            /**
             * Tarkistaa, onko tämä päivämäärä sama kuin toinen päivämäärä.
             *
             * @param obj toinen päivämäärä
             * @return true, jos päivämäärät ovat samat, muuten false
             */
            @Override
            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || getClass() != obj.getClass()) {
                    return false;
                }
                Paivays other = (Paivays) obj;
                if (date.equals(other.date)) {
                    Alert alert = new Alert(AlertType.CONFIRMATION);
                    alert.setTitle("Huomio!");
                    alert.setHeaderText("Päivämäärälle on jo syötetty tietoja");
                    alert.setContentText("Haluatko muokata päivämäärän tietoja?");
                    alert.showAndWait();
                    return true;
                }
                return false;
            }
        }

    /**
     * Tekstikenttä budjetin syöttämistä varten.
     * Tekstikenttä ruokakulujen syöttämistä varten.
     * Tekstikenttä vapaa-ajan kulujen syöttämistä varten.
     * Tekstikenttä muiden kulujen syöttämistä varten.
     */
    TextField tfBudjetti, tfRuokakulut, tfVapaaAjanKulut, tfMuutKulut;
    /**
     * Päivämäärän valitsin.
     */
    DatePicker datePvm;
    /**
     * Tekstialue tulostamaan budjetti- ja kulutiedot.
     */
    TextArea textArea;

    /**
     * Käynnistää sovelluksen käyttöliittymän.
     *
     * @param primaryStage ensisijainen ikkuna
     */
    @Override
    public void start(Stage primaryStage) {

        System.out.println("Ohjelma käynnistetty");

        //luodaan ja asetetaan ikkuna
        Pane pane = new Pane();

        //luodaan ja asetetaan elementit
        Label lbPvm = new Label("Päivämäärä:");
        lbPvm.setLayoutX(115);
        lbPvm.setLayoutY(180);
        lbPvm.setPrefSize(70, 50);
        pane.getChildren().add(lbPvm);

        datePvm = new DatePicker();
        datePvm.setLayoutX(215);
        datePvm.setLayoutY(190);
        datePvm.setPrefSize(180, 30);
        pane.getChildren().add(datePvm);

        Label lbBudjetti = new Label("Budjetti:");
        lbBudjetti.setLayoutX(115);
        lbBudjetti.setLayoutY(230);
        lbBudjetti.setPrefSize(70, 50);
        pane.getChildren().add(lbBudjetti);

        tfBudjetti = new TextField();
        tfBudjetti.setLayoutX(215);
        tfBudjetti.setLayoutY(240);
        tfBudjetti.setPrefSize(180, 30);
        pane.getChildren().add(tfBudjetti);

        Label lbRuokakulut = new Label("Ruokakulut:");
        lbRuokakulut.setLayoutX(115);
        lbRuokakulut.setLayoutY(290);
        lbRuokakulut.setPrefSize(175, 30);
        pane.getChildren().add(lbRuokakulut);

        tfRuokakulut = new TextField();
        tfRuokakulut.setLayoutX(215);
        tfRuokakulut.setLayoutY(290);
        tfRuokakulut.setPrefSize(180,30);
        pane.getChildren().add(tfRuokakulut);

        Label lbVapaaAjanKulut = new Label("Vapaa-ajan kulut:");
        lbVapaaAjanKulut.setLayoutX(115);
        lbVapaaAjanKulut.setLayoutY(340);
        lbVapaaAjanKulut.setPrefSize(175, 30);
        pane.getChildren().add(lbVapaaAjanKulut);

        tfVapaaAjanKulut = new TextField();
        tfVapaaAjanKulut.setLayoutX(215);
        tfVapaaAjanKulut.setLayoutY(340);
        tfVapaaAjanKulut.setPrefSize(180, 30);
        pane.getChildren().add(tfVapaaAjanKulut);

        Label lbMuutKulut = new Label("Muut kulut:");
        lbMuutKulut.setLayoutX(115);
        lbMuutKulut.setLayoutY(390);
        lbMuutKulut.setPrefSize(175, 30);
        pane.getChildren().add(lbMuutKulut);

        tfMuutKulut = new TextField();
        tfMuutKulut.setLayoutX(215);
        tfMuutKulut.setLayoutY(390);
        tfMuutKulut.setPrefSize(180, 30);
        pane.getChildren().add(tfMuutKulut);

        textArea = new TextArea();
        textArea.setLayoutX(430);
        textArea.setLayoutY(80);
        textArea.setPrefSize(400, 500);
        textArea.setEditable(false);
        pane.getChildren().add(textArea);

        //luodaan ja asetetaan napit
        Button btLaske = new Button("Laske");
        btLaske.setLayoutX(325);
        btLaske.setLayoutY(450);
        btLaske.setPrefSize(70, 30);
        pane.getChildren().add(btLaske);

        /**
         * Asettaa tapahtumankäsittelijän Laske-napille.
         * Kun nappia painetaan, suoritetaan laskeKulut()-metodi.
         *
         * @param e tapahtuma
         */
        btLaske.setOnAction(e -> {
            laskeKulut();
        });
        /**
         * ObservableMap, joka sisältää budjetti- ja kulutietorakenteen.
         */
        budjettiKulutMap = FXCollections.observableMap(new HashMap<>());
        /**
         * Lukee tiedostosta aiemmin tallennetut budjetti- ja kulutiedot ja päivittää ne budjettiKulutMap-tietorakenteeseen.
         */
        tiedostostaLukeminen();
        /**
         * Päivittää tekstialueen sisällön budjetti- ja kulutiedot vastaamaan budjettiKulutMap-tietorakenteen tilaa.
         */
        paivitaTekstikentta();

        /**
         * Lisää muutoksenkuuntelijan budjettiKulutMap-tietorakenteelle.
         * Kun tietorakenteessa tapahtuu muutoksia, suoritetaan paivitaTekstikentta()-metodi.
         *
         * @param change muutoksenkuuntelija
         */
        budjettiKulutMap.addListener((MapChangeListener<Paivays, BudjettiKulut>) change -> {
            tiedostoonTallentaminen();
            paivitaTekstikentta();
        });

        Scene scene = new Scene(pane, 930, 700);
        primaryStage.setTitle("Budjetti ja kulut");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    /**
     * Laskee kulut syötetyistä arvoista ja tallentaa ne budjettiKulutMap-tietorakenteeseen.
     * Tarkistaa syötteiden oikeellisuuden ja näyttää hälytyksiä tarvittaessa.
     * Tyhjentää syötekentät onnistuneen laskennan jälkeen.
     */
    private void laskeKulut() {

        double budjetti;
        double ruokakulut;
        double vapaa_ajan_kulut;
        double muut_kulut;

        // Tarkistetaan, ettei syötteitä ole jätetty tyhjiksi
        if(tfBudjetti.getText().isEmpty() || datePvm.getValue() == null){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Huomio");
            alert.setHeaderText("Päivämäärä tai budjetti on syöttämättä");
            alert.setContentText("Päivämäärä ja budjetti on pakko syöttää");
            alert.showAndWait();
            return;
        }

        try {
            budjetti = Double.parseDouble(tfBudjetti.getText());

            if(tfRuokakulut.getText().isEmpty()){
                ruokakulut = 0;
            } else {
                ruokakulut = Double.parseDouble(tfRuokakulut.getText());
            }

            if(tfVapaaAjanKulut.getText().isEmpty()){
                vapaa_ajan_kulut = 0;
            } else {
                vapaa_ajan_kulut = Double.parseDouble(tfVapaaAjanKulut.getText());
            }

            if(tfMuutKulut.getText().isEmpty()){
                muut_kulut = 0;
            } else {
                muut_kulut = Double.parseDouble(tfMuutKulut.getText());
            }
        }
        catch (NumberFormatException e){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Virheellinen syöte");
            alert.setHeaderText("Syötetty arvo ei ole numero");
            alert.setContentText("Syötä numeroarvo");
            alert.showAndWait();
            return;
        }

        double yhteenlasketutKulut = (ruokakulut + vapaa_ajan_kulut + muut_kulut);

        if(yhteenlasketutKulut > budjetti){
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Budjetti ylitetty!");
            alert.setHeaderText("Menot ylittävät budjetin!");
            alert.setContentText("Tarkkaile rahankäyttöä jatkossa..");
            alert.showAndWait();
        }

        LocalDate selectedDate = datePvm.getValue();
        Paivays paivays = new Paivays(selectedDate);

        // Luodaan uusi BudjettiKulut-olio ja lisätään se tietorakenteeseen
        BudjettiKulut budjettiKulut = new BudjettiKulut(budjetti, ruokakulut, vapaa_ajan_kulut, muut_kulut, selectedDate);
        budjettiKulutMap.put(paivays, budjettiKulut);

        // tiedostoonTallentaminen();

        // Tyhjennetään syöttökentät
        datePvm.getEditor().clear();
        tfBudjetti.clear();
        tfRuokakulut.clear();
        tfVapaaAjanKulut.clear();
        tfMuutKulut.clear();

    }

    /**
     * Lukee budjetti- ja kulutiedot tiedostosta ja päivittää ne budjettiKulutMap-tietorakenteeseen.
     * Tiedoston oletettu muoto:
     * Päivämäärä: [pvm]
     * Budjetti: [budjetti]
     * Ruokakulut: [ruokakulut]
     * Vapaa-ajan kulut: [vapaa-ajan kulut]
     * Muut kulut: [muut kulut]
     * Yhteenlasketut kulut: [yhteenlasketut kulut]
     * Budjetista jäi yli: [budjetista jäi yli]
     * --------------------------------------------------------------------------
     * Mahdolliset poikkeukset käsitellään ja tulostetaan virheviestit sekä konsoliin että tekstikenttään.
     */
    private void tiedostostaLukeminen() {
        File file = new File("kulut.txt");
        if (!file.exists()) {
            System.out.println("Tiedostoa ei löytynyt");
            return; // Jos tiedostoa ei ole, ei ladata dataa
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String rivi;
            while ((rivi = reader.readLine()) != null) {
                /// Jäsennä rivi ja luo uusi BudjettiKulut-olio
                String[] parts = rivi.split(": ");
                if (parts.length == 2 && parts[0].equals("Päivämäärä")) {
                    LocalDate pvm = LocalDate.parse(parts[1], DateTimeFormatter.ofPattern("dd.MM.yyyy"));
                    double budjetti = Double.parseDouble(reader.readLine().split(": ")[1]);
                    double ruokakulut = Double.parseDouble(reader.readLine().split(": ")[1]);
                    double vapaaAjanKulut = Double.parseDouble(reader.readLine().split(": ")[1]);
                    double muutKulut = Double.parseDouble(reader.readLine().split(": ")[1]);
                    reader.readLine(); // Skip "Yhteenlasketut kulut: ..."
                    reader.readLine(); // Skip "Budjetista jäi yli: ..."
                    reader.readLine(); // Skip "--------------------------------------------------------------------------"
                    Paivays paivays = new Paivays(pvm);
                    BudjettiKulut budjettiKulut = new BudjettiKulut(budjetti, ruokakulut, vapaaAjanKulut, muutKulut, pvm);
                    budjettiKulutMap.put(paivays, budjettiKulut);
                }
            }
            System.out.println("Tiedosto luettu");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Ongelma tiedoston lukemisessa!");
        }
    }

    /**
     * Tallentaa budjetti- ja kulutiedot tiedostoon "kulut.txt".
     * Tiedoston muoto:
     * Päivämäärä: [päivämäärä]
     * Budjetti: [budjetti]
     * Ruokakulut: [ruokakulut]
     * Vapaa-ajan kulut: [vapaa-ajan kulut]
     * Muut kulut: [muut kulut]
     * Yhteenlasketut kulut: [yhteenlasketut kulut]
     * Budjetista jäi yli: [budjetista jäi yli]
     * --------------------------------------------------------------------------
     * Mahdolliset poikkeukset käsitellään ja tulostetaan virheviestit sekä konsoliin että tekstikenttään.
     */
    private void tiedostoonTallentaminen() {

            try (FileWriter writer = new FileWriter("kulut.txt")) {
                for (Map.Entry<Paivays, BudjettiKulut> entry : budjettiKulutMap.entrySet()) {
                    BudjettiKulut budjettiKulut = entry.getValue();
                    writer.write("Päivämäärä: " + budjettiKulut.getPvmFormatted() + "\n");
                    writer.write("Budjetti: " + budjettiKulut.getBudjetti() + "\n");
                    writer.write("Ruokakulut: " + budjettiKulut.getRuokakulut() + "\n");
                    writer.write("Vapaa-ajan kulut: " + budjettiKulut.getVapaaAjanKulut() + "\n");
                    writer.write("Muut kulut: " + budjettiKulut.getMuutKulut() + "\n");
                    writer.write("Yhteenlasketut kulut: "
                            + (budjettiKulut.getRuokakulut() + budjettiKulut.getVapaaAjanKulut() + budjettiKulut.getMuutKulut())
                            + "\n");
                    writer.write("Budjetista jäi yli: "
                            + (budjettiKulut.getBudjetti()
                            - (budjettiKulut.getRuokakulut() + budjettiKulut.getVapaaAjanKulut() + budjettiKulut.getMuutKulut()))
                            + "\n");
                    writer.write("--------------------------------------------------------------------------\n");
                }
                System.out.println("Tiedosto tallennettu");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Ongelma tiedoston tallentamisessa!");
            }
        }


    /**
     * Päivittää tekstikentän näyttämään budjetti- ja kulutiedot. Metodi tyhjentää ensin tekstikentän ja
     * lisää sitten jokaisen budjetti- ja kulutiedon budjettiKulutMap-tietorakenteesta tekstikenttään.
     * Lopuksi lisätään erottava viiva jokaisen budjetti- ja kulutiedon väliin.
     * Mahdolliset poikkeukset käsitellään ja tulostetaan virheviestit sekä konsoliin että tekstikenttään.
     */
    private void paivitaTekstikentta() {

        textArea.clear();
        try {
            for (BudjettiKulut budjettiKulut : budjettiKulutMap.values()) {
                textArea.appendText("Päivämäärä: " + budjettiKulut.getPvmFormatted() + "\n");
                textArea.appendText("Budjetti: " + budjettiKulut.getBudjetti() + "\n");
                textArea.appendText("Ruokakulut: " + budjettiKulut.getRuokakulut() + "\n");
                textArea.appendText("Vapaa-ajan kulut: " + budjettiKulut.getVapaaAjanKulut() + "\n");
                textArea.appendText("Muut kulut: " + budjettiKulut.getMuutKulut() + "\n");
                textArea.appendText("Yhteenlasketut kulut: "
                        + (budjettiKulut.getRuokakulut() + budjettiKulut.getVapaaAjanKulut() + budjettiKulut.getMuutKulut())
                        + "\n");
                textArea.appendText("Budjetista jäi yli: "
                        + (budjettiKulut.getBudjetti()
                        - (budjettiKulut.getRuokakulut() + budjettiKulut.getVapaaAjanKulut() + budjettiKulut.getMuutKulut()))
                        + "\n");
                textArea.appendText("--------------------------------------------------------------------------\n");
            }
            System.out.println("Tekstikenttä päivitetty");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Ongelma tekstikentän päivittämisessä");
        }
    }
    /**
     * Metodi, joka kutsutaan sovelluksen sulkemisen yhteydessä. Se tallentaa budjetti- ja kulutiedot
     * tiedostoon ennen sovelluksen sulkemista. Metodi suorittaa `tiedostoonTallentaminen()`-metodin,
     * joka vastaa tiedostoon tallentamisesta.
     */
    @Override
    public void stop() {
        tiedostoonTallentaminen();
        System.out.println("Ohjelma suljettu");
    }

    /**
     * Sovelluksen pääsilmukka.
     * Käynnistää JavaFX-sovelluksen.
     *
     * @param args komentorivin argumentit
     */
    public static void main (String[]args){
        launch(args);
    }
}