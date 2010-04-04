package sprime.server

class PowerUsage {

    Date dateCreated;

    double wattage;

    static constraints = {
        sort dateCreated: "desc"
    }
}
