public class TreeLocatorMap<K extends Comparable<K>> implements LocatorMap<K> {
	Map <K,Location> map;
	Locator <K> locator;
	
	public TreeLocatorMap() {
		map = new BST<K, Location>();
		locator = new TreeLocator <K>();		
	}
	
	
	@Override
	public Map<K, Location> getMap() {
		return map;
	}

	@Override
	public Locator<K> getLocator() {
		return locator;
	}

	@Override
	public Pair<Boolean, Integer> add(K k, Location loc) {
		if(!(map.find(k).first)) {
			map.insert(k, loc);
			locator.add(k, loc);
			return new Pair<Boolean, Integer>(true, map.find(k).second );
		}
		return new Pair<Boolean, Integer>(false, map.find(k).second);
	}

	@Override
	public Pair<Boolean, Integer> move(K k, Location loc) {
		if((map.find(k).first)) {
			locator.remove(k,map.retrieve());
			map.update(loc);
			locator.add(k, loc);
			return new Pair<Boolean, Integer>(true, map.find(k).second);	
		}
		return new Pair<Boolean, Integer>(false, map.find(k).second);
	}

	@Override
	public Pair<Location, Integer> getLoc(K k) {
		if((map.find(k).first))
			return new Pair<Location, Integer>(map.retrieve(),map.find(k).second);
		
		return new Pair<Location, Integer>(null,map.find(k).second);
	}

	@Override
	public Pair<Boolean, Integer> remove(K k) {
		if((map.find(k).first)) {
			locator.remove(k, map.retrieve());
			map.remove(k);
			return new Pair<Boolean, Integer>(true, map.find(k).second);	
		}
		return new Pair<Boolean, Integer>(false, map.find(k).second);
	}


	@Override
	public List<K> getAll() {
		return map.getAll();
	}

	@Override
	public Pair<List<K>, Integer> getInRange(Location lowerLeft, Location upperRight) {
	
		List <K> Cars = new LinkedList <K>();
        List <Pair < Location , List <K>>> data = locator.inRange(lowerLeft, upperRight).first;
        
        if (data.empty() == false) {
            data.findFirst();
            while (data.last() == false) {
                    if (data.retrieve().second.empty() == false) {
                    	data.retrieve().second.findFirst(); 
                        while (data.retrieve().second.last() == false) {
                            Cars.insert(data.retrieve().second.retrieve());
                            data.retrieve().second.findNext();
                        }
                        Cars.insert(data.retrieve().second.retrieve());
                    }
                data.findNext();
            }

            if (data.retrieve().second.empty() == false) {
            	data.retrieve().second.findFirst();
                while (data.retrieve().second.last() == false) { 
                    Cars.insert(data.retrieve().second.retrieve());
                    data.retrieve().second.findNext();
                }
                Cars.insert(data.retrieve().second.retrieve());
            }
        }
       return new Pair <List <K>, Integer>(Cars, locator.inRange(lowerLeft, upperRight).second);
	}
	   
}
