import type { AxiosError, AxiosInstance, AxiosResponse } from 'axios';
import axios from 'axios';

export function isAxiosError(value: any): value is AxiosError {
  return typeof value?.response === 'object';
}

export abstract class ApiClient {
    protected readonly https: AxiosInstance;

    protected constructor(protected readonly path?: string, protected readonly baseURL: string = "") {
        if (path) {
            baseURL += path;
        }

        this.https = axios.create({ baseURL, headers: { "Content-Type": "application/json" } });
    }
}