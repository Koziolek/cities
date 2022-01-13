import {gql} from '@apollo/client';

export const GET_CITIES = gql` query Cities($page: Int!, $size: Int!){
    cities(pageNumber: $page, pageSize: $size)  {
        cities {
            id
            name
            photo
        }
        totalPages
        currentPage
    }
}`

export const CITY_BY_NAME = gql`query Cities($name:String!){
    cityByName(name: $name){
        cities{
            id
            name
            photo
        }
        totalPages
        currentPage
    }
}`

export const UPDATE_CITY = gql`mutation Cities($id: ID!, $name: String!, $photo: String!){
    updateCity(city: {id: $id, name: $name, photo: $photo}){
        id
        name
        photo
    }
}`