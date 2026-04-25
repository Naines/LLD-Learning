//package com.nainesh.lld.Uber;
//
//
//import com.nainesh.lld.Uber.model.Location;
//import com.nainesh.lld.Uber.model.Product;
//
//import java.util.List;
//import java.util.Map;
//
//public class Main {
//
//    public static void main(String[] args) {
//        FareService fs=new FareService();
//        //create the products and get fareEstimate
//        //return map<product, double>
//        List<Product> products = List.of(new Product("SUV", 1, 1), new Product("SEDAN", 2,2));
//        Location src = new Location(48.45, 46.19);
//        Location des = new Location(51.42, 52.56);
//        Map<Product, Price> PriceMap = fs.getFareEstimates(src, des, products);
//
//        //createFare - search the rider with given strategy - rating, distance
//        //create booking ttl entry
//
//
//        //booking after ttl should fail
//
//        //booking a single rider available with 2 threads
//    }
//
//}
