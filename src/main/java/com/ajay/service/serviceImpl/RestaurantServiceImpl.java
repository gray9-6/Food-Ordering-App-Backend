package com.ajay.service.serviceImpl;

import com.ajay.dtos.RestaurantDto;
import com.ajay.model.Address;
import com.ajay.model.Restaurant;
import com.ajay.model.User;
import com.ajay.repository.AddressRepository;
import com.ajay.repository.RestaurantRepository;
import com.ajay.repository.UserRepository;
import com.ajay.request.CreateRestaurantRequest;
import com.ajay.service.RestaurantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class RestaurantServiceImpl implements RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;
    @Autowired
    private AddressRepository addressRepository;
    @Autowired
    private UserRepository userRepository;


    /**
     * @param createRestaurantRequest
     * @param user
     * @return
     */
    @Override
    public Restaurant createRestaurant(CreateRestaurantRequest createRestaurantRequest, User user) {
        // get the restaurant address from the request and save the address
        Address address =  addressRepository.save(createRestaurantRequest.getAddress());

        // save the restaurant
        Restaurant restaurant = Restaurant.builder()
                .address(address)
                .contactInformation(createRestaurantRequest.getContactInformation())
                .cuisineType(createRestaurantRequest.getCuisineType())
                .description(createRestaurantRequest.getDescription())
                .images(createRestaurantRequest.getImages())
                .name(createRestaurantRequest.getName())
                .openingHours(createRestaurantRequest.getOpeningHours())
                .registrationDate(LocalDateTime.now())
                .open(true)
                .owner(user)
                .build();
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        return savedRestaurant;
    }

    /**
     * @param restaurantId
     * @param updatedRestaurant
     * @return
     * @throws Exception
     */
    @Override
    public Restaurant updateRestaurant(Long restaurantId, CreateRestaurantRequest updatedRestaurant) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);

        // need to add check , that the given fields should not be null
//        restaurant.setId(updatedRestaurant.getId());
//        restaurant.setAddress(updatedRestaurant.getAddress());
//        restaurant.setOpeningHours(updatedRestaurant.getOpeningHours());
//        restaurant.setImages(updatedRestaurant.getImages());
//        restaurant.setName(updatedRestaurant.getName());
//        restaurant.setContactInformation(updatedRestaurant.getContactInformation());
//        restaurant.setDescription(updatedRestaurant.getDescription());
        restaurant.setCuisineType(updatedRestaurant.getCuisineType());

        return restaurantRepository.save(restaurant);
    }

    /**
     * @param restaurantId
     * @throws Exception
     */
    @Override
    public void deleteRestaurant(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurantRepository.deleteById(restaurant.getId());
    }

    /**
     * @return
     */
    @Override
    public List<Restaurant> getAllRestaurant() {
        return restaurantRepository.findAll();
    }

    /**
     * @return
     */
    @Override
    public List<Restaurant> searchRestaurant(String keyWord) {
        return restaurantRepository.findBySearchQuery(keyWord);
    }

    /**
     * @param restaurantId
     * @return
     * @throws Exception
     */
    @Override
    public Restaurant findRestaurantById(Long restaurantId) throws Exception {
        Optional<Restaurant> optionalRestaurant = restaurantRepository.findById(restaurantId);
        if(optionalRestaurant.isEmpty()){
            throw new Exception("Restaurant Not found by this ID");
        }
        return optionalRestaurant.get();
    }

    /**
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public Restaurant getRestaurantByUserId(Long userId) throws Exception {
        return Optional.ofNullable(restaurantRepository.findByOwnerId(userId)).orElseThrow(()-> new Exception("Restaurant Not found by this owner ID !!"));
    }

    /**
     * @param restaurantId
     * @param user
     * @return
     * @throws Exception
     */
    @Override
    public RestaurantDto addToFavorites(Long restaurantId, User user) throws Exception {
        Optional<RestaurantDto> optionalRestaurantDto = user.getFavorites().stream().filter(favorite-> favorite.getId().equals(restaurantId)).findFirst();

        // prepare the response
        RestaurantDto restaurantDto = null;
        if(optionalRestaurantDto.isEmpty()){   // agar wo pehle se fav mein add nahi hai , tabhi add karnege
            Restaurant restaurant = findRestaurantById(restaurantId);

            restaurantDto = new RestaurantDto();
            restaurantDto.setDescription(restaurant.getDescription());
            restaurantDto.setImages(restaurant.getImages());
            restaurantDto.setTitle(restaurant.getName());
            restaurantDto.setId(restaurant.getId());

            user.getFavorites().add(restaurantDto);
        }else{
            user.getFavorites().removeIf(restaurant -> restaurant.getId().equals(restaurantId));
        }

        // save the user
        userRepository.save(user);

        return restaurantDto;
    }

    /**
     * @param restaurantId
     * @return
     * @throws Exception
     */
    @Override
    public Restaurant updateRestaurantStatus(Long restaurantId) throws Exception {
        Restaurant restaurant = findRestaurantById(restaurantId);
        restaurant.setOpen(!restaurant.isOpen());  // agar open hua toh close ho jaayega , and close hua toh open ho jaayega

        return restaurantRepository.save(restaurant);
    }
}
