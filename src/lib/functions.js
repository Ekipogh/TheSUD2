var GameCharacter = Packages.ru.ekipogh.sud.GameCharacter;
var Item = Packages.ru.ekipogh.sud.Item;
var Location = Packages.ru.ekipogh.sud.GameCharacter;

function getLocationById(locationId){
    for(i = 0; i < locations.size(); i++){
        if(locations.get(i).getId()==locationId)
            return locations.get(i);
    }
}

function getItemById(itemId){
    for(i = 0; i < items.size(); i++){
        if(items.get(i).getId()==itemId)
            return items.get(i);
    }
}

function getCharacterById(charId){
    for(i = 0; i < characters.size(); i++){
            if(characters.get(i).getId()==charId)
                return characters.get(i);
        }
}

function addItemToLocation(itemId, locationId){
    if(!isNaN(itemId) && !isNaN(locationId)){
        var location = getLocationById(locationId);
        var item = getItemById(itemId);
        location.addItem(item);
        game.updateItems();
    }
}

function spawnCharacter(charId){
    if(!isNaN(charId)){
        var char = getCharacterById(charId);
        var currentLocation = player.getLocation();
        char.setLocation(currentLocation);
        game.updateCharacters();
    }
}

