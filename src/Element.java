import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Element {

    private int atomicNumber;
    private String symbol;
    private String name;
    private int empirical_radius;
    private int calculated_radius;
    private double mass;




    public Element(int atomicNumber, String symbol, String name, int empirical_radius, int calculated_radius){
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
        this.name = name;
        this.empirical_radius = empirical_radius;
        this.calculated_radius = calculated_radius;
        this.mass = -1;

    }

    public void setMass(double mass){
        this.mass = mass;
    }

    public int getAtomicNumber() {
        return atomicNumber;
    }

    public int getCalculated_radius() {
        return calculated_radius;
    }

    public String getName() {
        return name;
    }

    public int getEmpirical_radius() {
        return empirical_radius;
    }

    public String getSymbol() {
        return symbol;
    }

    public double getMass(){
        return mass;
    }

    @Override
    public String toString() {
        return "Element{" +
                "atomicNumber=" + atomicNumber +
                ", symbol='" + symbol + '\'' +
                ", name='" + name + '\'' +
                ", empirical_radius=" + empirical_radius +
                ", calculated_radius=" + calculated_radius + ", mass=" + mass +
                '}';
    }



    public double calculateEmpericalDensity(){
        if(mass != -1.0 && this.calcEmpiricalVolume() !=-1){
            return mass/this.calcEmpiricalVolume();
        }else {
            return -1;
        }
    }

    private double calcEmpiricalVolume(){
        if(empirical_radius != -1){
            return (Math.PI*(Math.pow(getEmpirical_radius(),3)*4)/3);
        }else {
            return -1;
        }
    }

    private double calcCalculatedVolume(){
        if(getCalculated_radius() != -1){
            return (Math.PI*(Math.pow(getCalculated_radius(),3)*4)/3);
        }else {
            return -1;
        }
    }

    public double calculateCalculatedDensity(){
        if(mass != -1.0 && this.calcCalculatedVolume() !=-1.0){
            return (mass/this.calcCalculatedVolume());
        }else {
            return -1;
        }
    }

    public static void main(String[]args){

        //create the elements from file

        Element[] elements = new Element[118];
        String[] element = null;
        StringTokenizer st;

        try {
            BufferedReader br = new BufferedReader(new FileReader("src/atomicradii.txt"));
            int empirical;
            int calculated;


            for(int i = 0; i < 118;i++){
                st = new StringTokenizer(br.readLine());
                element = st.nextToken().split(",");

                if(element[3].equals("no data") || (element[3].equals("no"))){
                    empirical = -1;
                }else {
                    empirical = Integer.parseInt(element[3]);
                }
                if (element[4].equals("no data") || (element[4].equals("no"))){
                    calculated = -1;
                }else {
                    calculated = Integer.parseInt(element[4]);
                }


                elements[i] = new Element(Integer.parseInt(element[0]), element[1], element[2], empirical,calculated);
            }
        }catch (IOException e){
                e.printStackTrace();
        }

        //set mass from file
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/atomicmass.txt"));
            for(int i = 0; i < 118;i++){
                st = new StringTokenizer(br.readLine());
                elements[i].setMass(Double.parseDouble(st.nextToken()));
            }
        }catch (IOException e){
            e.printStackTrace();
        }

        for(int i=0;i < 118; i++){
            System.out.println(elements[i].toString());
        }


        //Calculation
        for(int i = 0; i < 118; i++){
            System.out.println("Calculated density: " + elements[i].symbol + ": " + elements[i].calculateCalculatedDensity());
            System.out.println("Emperical density: " + elements[i].symbol + ": " + elements[i].calculateEmpericalDensity() + "\n");
        }




    }


}
