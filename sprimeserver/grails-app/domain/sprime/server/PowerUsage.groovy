package sprime.server

class PowerUsage {

    Date dateCreated;

    double wattage;

    static mapping = {
        sort dateCreated: "desc"
    }
}
