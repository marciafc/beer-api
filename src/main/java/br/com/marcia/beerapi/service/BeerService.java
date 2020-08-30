package br.com.marcia.beerapi.service;

import br.com.marcia.beerapi.dto.BeerDTO;
import br.com.marcia.beerapi.entity.Beer;
import br.com.marcia.beerapi.exception.BeerAlreadyRegisteredException;
import br.com.marcia.beerapi.exception.BeerNotFoundException;
import br.com.marcia.beerapi.exception.BeerStockExceededException;
import br.com.marcia.beerapi.mapper.BeerMapper;
import br.com.marcia.beerapi.repository.BeerRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class BeerService {

    private final BeerRepository beerRepository;

    private final BeerMapper beerMapper = BeerMapper.INSTANCE;

    public BeerDTO createBeer(BeerDTO beerDTO) throws BeerAlreadyRegisteredException {

        verifyIfIsAlreadyRegistered(beerDTO.getName());
        Beer beer = beerMapper.toModel(beerDTO);
        Beer savedBeer = beerRepository.save(beer);
        return beerMapper.toDTO(savedBeer);
    }

    public BeerDTO findByName(String name) throws BeerNotFoundException {

        Beer foundBeer = beerRepository.findByName(name)
                .orElseThrow(() -> new BeerNotFoundException(name));
        return beerMapper.toDTO(foundBeer);
    }

    public List<BeerDTO> listAll() {

        return beerRepository.findAll()
                .stream()
                .map(beerMapper::toDTO)
                .collect(Collectors.toList());
    }

    public void deleteById(Long id) throws BeerNotFoundException {

        verifyIfExists(id);
        beerRepository.deleteById(id);
    }

    private void verifyIfIsAlreadyRegistered(String name) throws BeerAlreadyRegisteredException {

        Optional<Beer> optSavedBeer = beerRepository.findByName(name);
        if (optSavedBeer.isPresent()) {
            throw new BeerAlreadyRegisteredException(name);
        }
    }

    private Beer verifyIfExists(Long id) throws BeerNotFoundException {

        return beerRepository.findById(id)
                .orElseThrow(() -> new BeerNotFoundException(id));
    }

    public BeerDTO increment(Long id, int quantityToIncrement) throws BeerNotFoundException, BeerStockExceededException {

        Beer beerToIncrementStock = verifyIfExists(id);

        if (verifyIfCanIncrement(quantityToIncrement, beerToIncrementStock)) {

            Beer incrementedBeerStock = updateQuantity(beerToIncrementStock, beerToIncrementStock.getQuantity() + quantityToIncrement);
            return beerMapper.toDTO(incrementedBeerStock);
        }

        throw new BeerStockExceededException(id, quantityToIncrement);
    }

    public BeerDTO decrement(Long id, int quantityToDecrement) throws BeerNotFoundException, BeerStockExceededException {

        Beer beerToDecrementStock = verifyIfExists(id);

        if (verifyIfCanDecrement(quantityToDecrement, beerToDecrementStock)) {
            Beer decrementedBeerStock = updateQuantity(beerToDecrementStock, beerToDecrementStock.getQuantity() - quantityToDecrement);
            return beerMapper.toDTO(decrementedBeerStock);
        }

        throw new BeerStockExceededException(id, quantityToDecrement);
    }

    private boolean verifyIfCanIncrement(int quantityToIncrement, Beer beerToIncrementStock) {
        int quantityAfterIncrement = beerToIncrementStock.getQuantity() + quantityToIncrement;
        return quantityAfterIncrement <= beerToIncrementStock.getMax();
    }

    private boolean verifyIfCanDecrement(int quantityToDecrement, Beer beerToDecrementStock) {
        int beerStockAfterDecremented = beerToDecrementStock.getQuantity() - quantityToDecrement;
        return beerStockAfterDecremented >= 0;
    }


    private Beer updateQuantity(Beer beerToIncrementStock, int quantity) {
        beerToIncrementStock.setQuantity(quantity);
        return beerRepository.save(beerToIncrementStock);
    }
}