import { Campaign } from "@/models/Campaign";
import { LoginRegisterRequest } from "@/models/LoginRegisterRequest";
import { User } from "@/models/User";
import { CharacterClass, PlayerCharacter} from "@/models/PlayerCharacter";
import { AxiosResponse } from "axios";
import axiosInstance from "./axiosInstance";

const responseBody = <T>(response: AxiosResponse<T>) => response.data;

const requests = {
    get: <T>(url: string) => axiosInstance.get<T>(url).then(responseBody),
    post: <T>(url: string, body: any) => axiosInstance.post<T>(url, body).then(responseBody),
    put: <T>(url: string, body: any) => axiosInstance.put<T>(url, body).then(responseBody)
};

const user = {
    login(user: LoginRegisterRequest) {
        return requests.post<User>('user/login', user);
    },
    register(user: LoginRegisterRequest) {
        return requests.put<User>('user/register', user);
    }
};

const campaign = {
    getCampaignList() {
        return requests.get<Campaign[]>('campaign/list');
    },
    getActiveCampaign() {
        return requests.get<Campaign>('campaign/getActive');
    }
}

const playerCharacter = {
    getClassList() {
        return requests.get<CharacterClass[]>('pc/classList');
    },
    addPlayerCharacter(playerCharacter: PlayerCharacter) {
        return requests.put('pc/add', playerCharacter);
    }
}

const agent = {
    user,
    campaign,
    playerCharacter
};

export default agent;