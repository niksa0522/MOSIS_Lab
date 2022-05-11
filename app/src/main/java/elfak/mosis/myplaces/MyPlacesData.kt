package elfak.mosis.myplaces

object MyPlacesData {



    var myPlaces: MutableList<MyPlace> = mutableListOf()

    init{
        myPlaces.add(MyPlace("cair","temp"))
        myPlaces.add(MyPlace("park svetog save","temp"))
        myPlaces.add(MyPlace("bubanj","temp"))
        myPlaces.add(MyPlace("test","temp"))

    }


    fun addNewPlace(place:MyPlace){
        myPlaces.add(place)
    }
    fun getPlace(poz: Int):MyPlace{
        return myPlaces[poz]
    }
    fun deletePlace(poz: Int){
        myPlaces.removeAt(poz)
    }

}

