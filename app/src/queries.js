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

