package com.beathive.service.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
/*
import com.beathive.model.CanadaTerritory;
import com.beathive.model.Country;
import com.beathive.model.Energy;
import com.beathive.model.Feel;
import com.beathive.model.Format;
import com.beathive.model.Genre;
import com.beathive.model.Instrument;
import com.beathive.model.Instrumentgroup;
import com.beathive.model.Keynote;
import com.beathive.model.Mood;
import com.beathive.model.Origin;
import com.beathive.model.Passage;
import com.beathive.model.Scale;
import com.beathive.model.Sonority;
import com.beathive.model.State;
import com.beathive.model.Status;
import com.beathive.model.Texture;
import com.beathive.model.Timesignature;
import com.beathive.model.Timespan;
*/
import com.beathive.dao.LookupDao;
import com.beathive.model.CanadaTerritory;
import com.beathive.model.Country;
import com.beathive.model.Genre;
import com.beathive.model.LabelValue;
import com.beathive.model.Registrant;
import com.beathive.model.Role;
import com.beathive.model.State;
import com.beathive.service.LookupManager;
import com.beathive.util.ConvertUtil;


/**
 * Implementation of LookupManager interface to talk to the persistence layer.
 *
 * <p><a href="LookupManagerImpl.java.html"><i>View Source</i></a></p>
 *
 * @author Ronald Dennison
 */
public class LookupManagerImpl extends BaseManager implements LookupManager {
    //~ Instance fields ========================================================

    private LookupDao dao;

    //~ Methods ================================================================

    public void setLookupDao(LookupDao dao) {
        super.dao = dao;
        this.dao = dao;
    }
	/**
	 * @see com.beathive.service.LookupManager#getAllFormats()
	 */
/*	public List getAllFormats() {
		List formats = dao.getFormats();
		List list = new LinkedList();
		Format format = null;
		log.info("all format: " + formats.size());
		for (int i = 0; i < formats.size(); i++) {
			format = (Format) formats.get(i);
			list.add(new LabelValue(format.getLabelKey(), format.getId()
							.toString()));
		}
		return list;

	}
*/
	/**
	 * @see com.beathive.service.LookupManager#getAllRoles()
	 */
	public List getAllRoles() {
		List roles = dao.getRoles();
		List list = new LinkedList();
		Role role = null;

		for (int i = 0; i < roles.size(); i++) {
			role = (Role) roles.get(i);
			list.add(new LabelValue(role.getName(), role.getName()));
		}

		return list;
	}

	/**
	 * 
	 * @see com.beathive.service.LookupManager#getAllGenres()
	 * @return
	 */

	public List getAllGenres() {
		List genres = dao.getGenres();
		List list = new LinkedList();
		Genre genre = null;
		log.info("all genre: " + genres.size());
		for (int i = 0; i < genres.size(); i++) {
			genre = (Genre) genres.get(i);

			list.add(new LabelValue(genre.getLabelKey(), genre.getId().toString()));
		}
		return list;
	}

	/**
	 * 
	 * @see com.beathive.service.LookupManager#getAllStates()
	 * @return
	 */

	public List getAllStates() {
		List states = dao.getStates();
		List list = new LinkedList();
		State state = null;
		log.info("all states: " + states.size());
		for (int i = 0; i < states.size(); i++) {
			state = (State) states.get(i);
			list.add(new LabelValue(state.getLabel(), state.getValue()));
		}
		return list;
	}

	/**
	 * 
	 * @see com.beathive.service.LookupManager#getAllCountries()
	 * @return
	 */

	public List getAllCountries() {
		List countries = dao.getCountries();
		List list = new LinkedList();
		Country country = null;
		log.info("all countries: " + countries.size());
		for (int i = 0; i < countries.size(); i++) {
			country = (Country) countries.get(i);
			list.add(new LabelValue(country.getLabel(), country.getValue()));
		}
		return list;
	}


	public List getAllCanadaTerritories() {
		List countries = dao.getAllCanadaTerritories();
		List list = new LinkedList();
		CanadaTerritory territory = null;
		log.info("all territory: " + countries.size());
		for (int i = 0; i < countries.size(); i++) {
			territory = (CanadaTerritory) countries.get(i);
			list.add(new LabelValue(territory.getLabel(), territory.getValue()));
		}
		return list;
	}

	/**
	 * @see com.beathive.service.LookupManager#getAllInstruments()
	 * @return
/*
	public List getAllInstruments() {
		List<Instrument> instruments = dao.getInstruments();
		List<InstrumentForm> list = new LinkedList<InstrumentForm>();
		Instrument instrument = null;
		Instrumentgroup group = null;

		for (int i = 0; i < instruments.size(); i++) {
			instrument = (Instrument) instruments.get(i);
			new LabelObject(instrument.getLabelKey(), instrument.getId()
					.toString() , instrument.getInstrumentgroup().getId())
			list.add();
		}
		log.debug("all instruments: " + instruments.size());
		return list;
	}
*/

	/**
	 * @see com.beathive.service.LookupManager#getAllTimesignatures()
	 * @return
	 */
/*
	public List getAllTimesignatures() {
		List timesignatures = dao.getTimesignatures();
		List list = new LinkedList();
		Timesignature sign = null;

		for (int i = 0; i < timesignatures.size(); i++) {
			sign = (Timesignature) timesignatures.get(i);
			list.add(new LabelValue(sign.getLabelKey(), sign.getLabelKey()));
		}

		return list;
	}
*/
	/**
	 * @see com.beathive.service.LookupManager#getAllScales()
	 * @return
	 */
/*	public List getAllScales() {
		List scales = dao.getScales();
		List list = new LinkedList();
		Scale scale = null;
		log.info("all scales: " + scales.size());

		for (int i = 0; i < scales.size(); i++) {
			scale = (Scale) scales.get(i);
			list.add(new LabelValue(scale.getLabelKey(), scale.getLabelKey()));
		}

		return list;
	}
*/
	/**
	 * @see com.beathive.service.LookupManager#getAllKeynotes()
	 * @return
	 */
/*
	public List getAllKeynotes() {
		List keynotes = dao.getKeynotes();
		List list = new LinkedList();
		Keynote keynote = null;
		log.info("all keynotes: " + keynotes.size());

		for (int i = 0; i < keynotes.size(); i++) {
			keynote = (Keynote) keynotes.get(i);
			list.add(new LabelValue(keynote.getLabelKey(), keynote.getLabelKey()));
		}

		return list;
	}
*/
	/**
	 * Returns a List of all Passage
	 * 
	 * @return List
	 */
/*
	public List getAllPassages() {
		List passages = dao.getPassages();
		List list = new LinkedList();
		Passage passage = null;
		log.info("all passages: " + passages.size());

		for (int i = 0; i < passages.size(); i++) {
			passage = (Passage) passages.get(i);
			list.add(new LabelValue(passage.getLabelKey(), passage.getLabelKey()));
		}

		return list;
	}
*/
	/** returns a List of model objects including instrumentgroup*/

	
	
	public List getDescriptors(){
		List list = new LinkedList();
		list.addAll(dao.getGenres());
		list.addAll(dao.getTempos());
		List descs = dao.getDescriptors();
		list.addAll(descs);
//       	log.debug("raw descr: " + list);
       	return list;
	}


	
	/**
	 * Returns a List of all Energy
	 * 
	 * @return List
	 */

/*	
	public List getAllEnergies() {
		List energies = dao.getEnergies();
		List list = new LinkedList();
		Energy energy = null;
		log.info("all energies: " + energies.size());

		for (int i = 0; i < energies.size(); i++) {
			energy = (Energy) energies.get(i);
			list.add(new LabelValue(energy.getLabelKey(), energy.getLabelKey()));
		}

		return list;
	}
*/
	/**
	 * Returns a List of all Passage
	 * 
	 * @return List
	 */
/*
	public List getAllFeels() {
		List feels = dao.getFeels();
		List list = new LinkedList();
		Feel feel = null;
		log.info("all feels: " + feels.size());

		for (int i = 0; i < feels.size(); i++) {
			feel = (Feel) feels.get(i);
			list.add(new LabelValue(feel.getLabelKey(), feel.getLabelKey()));
		}

		return list;
	}
*/
	/**
	 * Returns a List of all Mood
	 * 
	 * @return List
	 */
/*
	public List getAllMoods() {
		List moods = dao.getMoods();
		List list = new LinkedList();
		Mood mood = null;
		log.info("all moods: " + moods.size());

		for (int i = 0; i < moods.size(); i++) {
			mood = (Mood) moods.get(i);
			list.add(new LabelValue(mood.getLabelKey(), mood.getLabelKey()));
		}

		return list;
	}
*/
	/**
	 * Returns a List of all Origin
	 * 
	 * @return List
	 */
/*
	public List getAllOrigins() {
		List origins = dao.getOrigins();
		List list = new LinkedList();
		Origin origin = null;
		log.info("all origins: " + origins.size());

		for (int i = 0; i < origins.size(); i++) {
			origin = (Origin) origins.get(i);
			list.add(new LabelValue(origin.getLabelKey(), origin.getLabelKey()));
		}

		return list;
	}
*/
	/**
	 * Returns a List of all Sonority
	 * 
	 * @return List
	 */
/*
	public List getAllSonorities() {
		List sonorities = dao.getSonorities();
		List list = new LinkedList();
		Sonority sonoritie = null;
		log.info("all sonorities: " + sonorities.size());

		for (int i = 0; i < sonorities.size(); i++) {
			sonoritie = (Sonority) sonorities.get(i);
			list.add(new LabelValue(sonoritie.getLabelKey(), sonoritie.getLabelKey()));
		}

		return list;
	}
*/
	/**
	 * Returns a List of all Tempo
	 * 
	 * @return List
	 */

	public List getAllTempos() {
		return dao.getTempos();
	}

	/**
	 * Returns a List of all Prices
	 * 
	 * @return List
	 */
/*	public List getAllPrices() {
		List prices = dao.getPrices();
		List list = new LinkedList();
		Price price = null;
		log.info("all prices: " + prices.size());

		for (int i = 0; i < prices.size(); i++) {
			price = (Price) prices.get(i);
			list.add(new LabelValue("" + price.getCost(), price.getId()
					.toString()));
		}
		return list;
	}
*/
	/**
	 * Returns a List of all Status
	 * 
	 * @return List
	 */
/*	public List getAllStati() {
		List clipStati = dao.getStati();
		List list = new LinkedList();
		Status clipStatus = null;
		log.info("all clipStati: " + clipStati.size());

		for (int i = 0; i < clipStati.size(); i++) {
			clipStatus = (Status) clipStati.get(i);
			list.add(new LabelValue(clipStatus.getLabelKey(), clipStatus
					.getId().toString()));
		}
		return list;
	}
*/
	/**
	 * Returns a List of all Texture
	 * 
	 * @return List
	 */
/*	public List getAllTextures() {
		List textures = dao.getTextures();
		List list = new LinkedList();
		Texture texture = null;
		log.info("all textures: " + textures.size());

		for (int i = 0; i < textures.size(); i++) {
			texture = (Texture) textures.get(i);
			list.add(new LabelValue(texture.getLabelKey(), texture.getLabelKey()));
		}

		return list;
	}
*/
	/**
	 * Returns a List of all Timespans
	 * 
	 * @return List
	 */
/*	public List getAllTimespans() {
		List timespans = dao.getTimespans();
		List list = new LinkedList();
		Timespan timespan = null;
		log.info("all timespans: " + timespans.size());

		for (int i = 0; i < timespans.size(); i++) {
			timespan = (Timespan) timespans.get(i);
			list.add(new LabelValue(timespan.getLabelKey(), ""
					+ timespan.getLabelKey()));
		}
		return list;
	}
*/
	/**
	 * returns the model objects
	 */
/*	public List getAllInstrumentGroups() {
			return dao.getInstrumentgroups();
	}
*/	
/*	public List getExchangeRates(){
		List exchanges = dao.getExchangeRates();
		log.info("all exchange rates: " + exchanges.size());
		return exchanges;
	}
*/
/*	
	public List getProducerPicks() {
		List list = dao.getProducerPicks();
		List picks = null;
		if (list != null){
			picks = new LinkedList(list);
		}
		return picks;
	}
*/
/*	public List getStores() {
		List list = dao.getStores();
		return list;
	}

	public List getCountries() {
		List countries = dao.getCountries();
		List list = new LinkedList();
		Country country = null;
		log.info("all countries: " + countries.size());
		for (int i = 0; i < countries.size(); i++) {
			country = (Country) countries.get(i);
			list.add(new LabelValue(country.getLabel(), country.getValue()));
		}
		return list;
	}

	public List getAllCanadaTerritories() {
		List countries = dao.getAllCanadaTerritories();
		List list = new LinkedList();
		CanadaTerritory territory = null;
		log.info("all countries: " + countries.size());
		for (int i = 0; i < countries.size(); i++) {
			territory = (CanadaTerritory) countries.get(i);
			list.add(new LabelValue(territory.getLabel(), territory.getValue()));
		}
		return list;
	}
*/
	
	public List getProducerUnboundAudio(String storeId) {
		return dao.getProducerUnboundAudio(new Long(storeId));
	}
	
	public List getClipAudio(String clipId){
		return dao.getClipAudio(new Long(clipId));
	}
	/* (non-Javadoc)
	 * @see com.beathive.service.LookupManager#getRegistrants()
	 */
	public List getRegistrants(List ids) {
		return dao.getRegistrants(ids);
	}
	/* (non-Javadoc)
	 * @see com.beathive.service.LookupManager#updateRegistrants(java.util.List)
	 */
	public void updateRegistrants(List<Registrant> registrants) {
		 dao.updateRegistrants(registrants);
	}
	public void deleteRegistrants(List<Registrant> registrants) {
		dao.deleteRegistrants(registrants);
	}
}
