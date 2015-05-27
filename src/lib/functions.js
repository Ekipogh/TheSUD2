function addItemToLocation(itemId, locationId){
    var location = findLocation(locationId);
    var item = findItem(itemId);
    location.addItem(item);
    game.updateItems();
}

function findLocation(locationId){
    for(i = 0; i < locations.size(); i++){
        if(locations.get(i).getId()==locationId)
            return locations.get(i);
    }
}

function findItem(itemId){
    for(i = 0; i < items.size(); i++){
        if(items.get(i).getId()==itemId)
            return items.get(i);
    }
}