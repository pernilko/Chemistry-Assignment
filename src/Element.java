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
    private double stated_density;
    private final double avogadros = 6.02214076e+23;




    public Element(int atomicNumber, String symbol, String name, int empirical_radius, int calculated_radius){
        this.atomicNumber = atomicNumber;
        this.symbol = symbol;
        this.name = name;
        this.empirical_radius = empirical_radius;
        this.calculated_radius = calculated_radius;
        this.stated_density = -1;
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

    public double getStated_density() { return stated_density; }

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

    public void setStated_density(double stated_density) {
        this.stated_density = stated_density;
    }

    public double calcDifferenceInEmpiricalDensity(){
        if(getStated_density() != -1 && this.calculateEmpericalDensity() != -1){
           return Math.abs(getStated_density() - this.calculateEmpericalDensity());
        }else {
            return -1;
        }
    }

    public double calcDifferenceInCalculatedDensity(){
        if(getStated_density() != -1 && this.calculateCalculatedDensity() != -1){
            return Math.abs(getStated_density() - this.calculateCalculatedDensity());
        }else {
            return -1;
        }
    }

    public double calculateEmpiricalDistaceBetweenAtoms(){
        if(this.calcDifferenceInEmpiricalDensity() != -1){
            double num_atoms =((getStated_density()*avogadros)/getMass());
            return 1/(Math.pow(num_atoms, 0.33333333));
        }
        return -1;
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

        //read density from file
        double density[] = new double[118];
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/atomicdensity.txt"));
            for(int i = 0; i < 118;i++){
                st = new StringTokenizer(br.readLine());
                elements[i].setStated_density(Double.parseDouble(st.nextToken()));
            }
        }catch (IOException e){
            e.printStackTrace();
        }



        //Calculation
        for(int i = 0; i < 118; i++){
            System.out.println(elements[i].getName() + " ("+ elements[i].getSymbol() + "):\n");
            System.out.println("Calculated density:" + elements[i].calculateCalculatedDensity());
            System.out.println("Emperical density: " + elements[i].calculateEmpericalDensity());
            System.out.println("Difference between stated density and empirical: " + elements[i].calcDifferenceInEmpiricalDensity());
            System.out.println("Difference between stated density and calculated: " + elements[i].calcDifferenceInCalculatedDensity());
            System.out.println("Distance between atoms:  " + elements[i].calculateEmpiricalDistaceBetweenAtoms() + "\n");
        }





    }


}
