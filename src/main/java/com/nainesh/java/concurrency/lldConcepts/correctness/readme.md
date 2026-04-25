1. check then act
check and act happen together

##3
boolean bookSeat(String seatId, String vId){
    if(seat.isAvailable){
        seat.setOcuppied(vId); //wrong way
        return true;
    }   
    return false;
}

Use atomic boolean here

##
2. read then modify
read+modify together

void increment(){
    count++;  //wont work
}
use atomic integer here

AtomicInteger count = new AtomicInteger(0);
count.IncrementAndGet();